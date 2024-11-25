CREATE DATABASE IF NOT EXISTS study;
USE study;

create table user (
    age integer not null,
    id bigint not null auto_increment,
    email varchar(255) not null,
    username varchar(255) not null,
    primary key (id)) engine=InnoDB

create table product (
    id bigint not null auto_increment,
    name varchar(255) not null,
    count int not null
    primary key (id))
    engine=InnoDB

INSERT INTO user(username, age, email) VALUES ('admin', 12, 'admin@example.com');
INSERT INTO user(username, age, email) VALUES ('eddy', 12, 'ktest92@gmail.com');

INSERT INTO product(name, count) VALUES ('사과', 12);
INSERT INTO product(name, count) VALUES ('수박', 33);