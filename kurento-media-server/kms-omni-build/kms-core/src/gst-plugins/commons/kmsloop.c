/*
 * (C) Copyright 2013 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include <gst/gst.h>
#include "kmsloop.h"

#define NAME "kmsloop"

GST_DEBUG_CATEGORY_STATIC (kms_loop_debug_category);
#define GST_CAT_DEFAULT kms_loop_debug_category

struct _KmsLoopPrivate
{
  GThread *thread;
  GRecMutex rmutex;
  GMainLoop *loop;
  GMainContext *context;

  GCond init_cond;
  GMutex init_mutex;
  gboolean initialized;
};

G_DEFINE_TYPE_WITH_PRIVATE (KmsLoop, kms_loop,
                                G_TYPE_OBJECT)

//G_DEFINE_TYPE_WITH_CODE (KmsLoop, kms_loop,
//    G_TYPE_OBJECT,
//    GST_DEBUG_CATEGORY_INIT (kms_loop_debug_category, NAME,
//        0, "debug category for kurento loop"));

#define KMS_LOOP_GET_PRIVATE(obj) ( \
  G_TYPE_INSTANCE_GET_PRIVATE (     \
    (obj),                          \
    KMS_TYPE_LOOP,                  \
    KmsLoopPrivate                  \
  )                                 \
)

#define KMS_LOOP_LOCK(elem) \
  (g_rec_mutex_lock (&KMS_LOOP ((elem))->priv->rmutex))
#define KMS_LOOP_UNLOCK(elem) \
  (g_rec_mutex_unlock (&KMS_LOOP ((elem))->priv->rmutex))

/* Object properties */
enum
{
  PROP_0,
  PROP_CONTEXT,
  N_PROPERTIES
};

static GParamSpec *obj_properties[N_PROPERTIES] = { NULL, };

static gboolean
quit_main_loop (KmsLoop * self)
{
  GST_DEBUG_OBJECT (self, "Quit GMainLoop");

  g_main_loop_quit (self->priv->loop);

  return G_SOURCE_REMOVE;
}

static gpointer
loop_thread_init (gpointer data)
{
  KmsLoop *self = KMS_LOOP (data);
  GMainLoop *loop;
  GMainContext *context;

  KMS_LOOP_LOCK (self);
  self->priv->context = g_main_context_new ();
  context = g_main_context_ref (self->priv->context);
  self->priv->loop = g_main_loop_new (context, FALSE);
  loop = g_main_loop_ref (self->priv->loop);
  KMS_LOOP_UNLOCK (self);

  // Set context as owned by this thread.
  if (!g_main_context_acquire (context)) {
    GST_ERROR_OBJECT (self, "Cannot acquire ownership of GMainContext");
    goto end;
  }

  // Set context as the thread-default main context for this thread.
  g_main_context_push_thread_default (context);

  // Unlock main thread because context is already initialized.
  g_mutex_lock (&self->priv->init_mutex);
  self->priv->initialized = TRUE;
  g_cond_signal (&self->priv->init_cond);
  g_mutex_unlock (&self->priv->init_mutex);

  GST_DEBUG_OBJECT (self, "Running GMainLoop on its own thread");
  g_main_loop_run (loop);

  g_main_context_pop_thread_default (context);
  g_main_context_release (context);

end:
  GST_DEBUG_OBJECT (self, "GMainLoop thread finished");
  g_main_loop_unref (loop);
  g_main_context_unref (context);

  return NULL;
}

static void
kms_loop_get_property (GObject * object, guint property_id, GValue * value,
    GParamSpec * pspec)
{
  KmsLoop *self = KMS_LOOP (object);

  KMS_LOOP_LOCK (self);

  switch (property_id) {
    case PROP_CONTEXT:
      g_value_set_boxed (value, self->priv->context);
      break;
    default:
      G_OBJECT_WARN_INVALID_PROPERTY_ID (object, property_id, pspec);
      break;
  }

  KMS_LOOP_UNLOCK (self);
}

static void
kms_loop_dispose (GObject * obj)
{
  KmsLoop *self = KMS_LOOP (obj);

  GST_LOG_OBJECT (self, "Dispose");

  KMS_LOOP_LOCK (self);

  if (self->priv->thread != NULL) {
    if (g_thread_self () != self->priv->thread) {
      GThread *aux = self->priv->thread;

      kms_loop_idle_add (self, (GSourceFunc) quit_main_loop, self);
      self->priv->thread = NULL;
      KMS_LOOP_UNLOCK (self);
      g_thread_join (aux);
      KMS_LOOP_LOCK (self);
    } else {
      /* self thread does not need to wait for itself */
      quit_main_loop (self);
      g_thread_unref (self->priv->thread);
      self->priv->thread = NULL;
    }
  }

  KMS_LOOP_UNLOCK (self);

  G_OBJECT_CLASS (kms_loop_parent_class)->dispose (obj);
}

static void
kms_loop_finalize (GObject * obj)
{
  KmsLoop *self = KMS_LOOP (obj);

  GST_LOG_OBJECT (obj, "Finalize");

  if (self->priv->context != NULL) {
    g_main_context_unref (self->priv->context);
  }

  if (self->priv->loop != NULL) {
    g_main_loop_unref (self->priv->loop);
  }

  g_rec_mutex_clear (&self->priv->rmutex);
  g_mutex_clear (&self->priv->init_mutex);
  g_cond_clear (&self->priv->init_cond);

  G_OBJECT_CLASS (kms_loop_parent_class)->finalize (obj);
}

static void
kms_loop_class_init (KmsLoopClass * klass)
{
  GObjectClass *objclass = G_OBJECT_CLASS (klass);

  objclass->dispose = kms_loop_dispose;
  objclass->finalize = kms_loop_finalize;
  objclass->get_property = kms_loop_get_property;

  /* Install properties */
  obj_properties[PROP_CONTEXT] = g_param_spec_boxed ("context",
      "GMainContext polled by the thread's GMainLoop",
      "GMainContext polled by the thread's GMainLoop",
      G_TYPE_MAIN_CONTEXT, (GParamFlags) (G_PARAM_READABLE));

  g_object_class_install_properties (objclass, N_PROPERTIES, obj_properties);

  /* Registers a private structure for the instantiatable type */
  // g_type_class_add_private (klass, sizeof (KmsLoopPrivate));
}

static void
kms_loop_init (KmsLoop * self)
{
//  self->priv = KMS_LOOP_GET_PRIVATE (self);
  self->priv = kms_loop_get_instance_private (self);
  self->priv->context = NULL;
  self->priv->loop = NULL;
  g_rec_mutex_init (&self->priv->rmutex);
  g_cond_init (&self->priv->init_cond);
  g_mutex_init (&self->priv->init_mutex);

  self->priv->thread = g_thread_new ("KmsLoop", loop_thread_init, self);

  g_mutex_lock (&self->priv->init_mutex);
  while (!self->priv->initialized) {
    g_cond_wait (&self->priv->init_cond, &self->priv->init_mutex);
  }
  g_mutex_unlock (&self->priv->init_mutex);

  GST_DEBUG_OBJECT (self, "New thread: %p", self->priv->thread);
}

KmsLoop *
kms_loop_new (void)
{
  GObject *loop;

  loop = g_object_new (KMS_TYPE_LOOP, NULL);

  return KMS_LOOP (loop);
}

static guint
kms_loop_attach (KmsLoop * self, GSource * source, gint priority,
    GSourceFunc function, gpointer data, GDestroyNotify notify)
{
  guint id;

  KMS_LOOP_LOCK (self);

  if (self->priv->thread == NULL) {
    KMS_LOOP_UNLOCK (self);
    return 0;
  }

  g_source_set_priority (source, priority);
  g_source_set_callback (source, function, data, notify);
  id = g_source_attach (source, self->priv->context);

  KMS_LOOP_UNLOCK (self);

  return id;
}

guint
kms_loop_idle_add_full (KmsLoop * self, gint priority, GSourceFunc function,
    gpointer data, GDestroyNotify notify)
{
  GSource *source;
  guint id;

  if (!KMS_IS_LOOP (self))
    return 0;

  source = g_idle_source_new ();
  id = kms_loop_attach (self, source, priority, function, data, notify);
  g_source_unref (source);

  return id;
}

guint
kms_loop_idle_add (KmsLoop * self, GSourceFunc function, gpointer data)
{
  return kms_loop_idle_add_full (self, G_PRIORITY_DEFAULT_IDLE, function, data,
      NULL);
}

guint
kms_loop_timeout_add_full (KmsLoop * self, gint priority, guint interval,
    GSourceFunc function, gpointer data, GDestroyNotify notify)
{
  GSource *source;
  guint id;

  if (!KMS_IS_LOOP (self))
    return 0;

  source = g_timeout_source_new (interval);
  id = kms_loop_attach (self, source, priority, function, data, notify);
  g_source_unref (source);

  return id;
}

guint
kms_loop_timeout_add (KmsLoop * self, guint interval, GSourceFunc function,
    gpointer data)
{
  return kms_loop_timeout_add_full (self, G_PRIORITY_DEFAULT, interval,
      function, data, NULL);
}

gboolean
kms_loop_remove (KmsLoop * self, guint source_id)
{
  GSource *source;

  source = g_main_context_find_source_by_id (self->priv->context, source_id);

  if (source == NULL) {
    return FALSE;
  }

  g_source_destroy (source);
  return TRUE;
}

gboolean
kms_loop_is_current_thread (KmsLoop * self)
{
  return (g_thread_self () == self->priv->thread);
}
