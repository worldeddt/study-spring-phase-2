//package springstudy.session.config;
//
//import jakarta.servlet.http.HttpSessionEvent;
//import jakarta.servlet.http.HttpSessionListener;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class SessionEventListener implements HttpSessionListener {
//
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent se) {
//        // 세션이 종료되면 userSessionMap에서 제거
//        log.debug("when close session: {}", se.getSession().getId());
//        customAuthenticationSuccessHandler.removeSession(se.getSession());
//    }
//}