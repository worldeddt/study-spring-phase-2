

### master a

```sql 
CREATE USER 'replicator'@'%' IDENTIFIED BY 'eddy';
GRANT REPLICATION SLAVE ON *.* TO 'replicator'@'%';
FLUSH PRIVILEGES;
```

```sql
CHANGE REPLICATION SOURCE TO
  SOURCE_HOST='mysql-master-b',
  SOURCE_USER='replicator',
  SOURCE_PASSWORD='eddy',
  SOURCE_LOG_FILE='mysql-bin.000001',
  SOURCE_LOG_POS=4;
START REPLICA;
```

### master b

```sql
CHANGE REPLICATION SOURCE TO
SOURCE_HOST='mysql-master-a',
SOURCE_USER='replicator',
SOURCE_PASSWORD='eddy',
SOURCE_LOG_FILE='mysql-bin.000001',
SOURCE_LOG_POS=4;
START REPLICA;
```
