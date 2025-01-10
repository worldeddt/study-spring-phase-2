

### master a

```sql 
CREATE USER 'replicator'@'%' IDENTIFIED BY 'eddy';
GRANT REPLICATION SLAVE ON *.* TO 'replicator'@'%';
FLUSH PRIVILEGES;
```

```sql
CHANGE MASTER TO 
MASTER_HOST='mysql-master-b',
MASTER_USER='replicator',
MASTER_PASSWORD='eddy',
MASTER_LOG_FILE='mysql-bin.000001',
MASTER_LOG_POS=4;
START SLAVE;
```

### master b

```sql
CHANGE MASTER TO 
MASTER_HOST='mysql-master-a',
MASTER_USER='replicator',
MASTER_PASSWORD='eddy',
MASTER_LOG_FILE='mysql-bin.000001',
MASTER_LOG_POS=4;
START SLAVE;
```
