CREATE DATABASE IF NOT EXISTS study;
USE study;

drop table if exists item
drop table if exists order_item
drop table if exists order_item_seq
drop table if exists orders
drop table if exists orders_seq
drop table if exists product
drop table if exists user
create table item (price integer not null, stock_quantity integer not null, item_id bigint not null auto_increment, dtype varchar(31) not null, name varchar(255), primary key (item_id)) engine=InnoDB
create table order_item (count integer not null, order_price integer not null, item_id bigint, order_id bigint, order_item_id bigint not null, primary key (order_item_id)) engine=InnoDB
create table order_item_seq (next_val bigint) engine=InnoDB
insert into order_item_seq values ( 1 )
create table orders (order_id bigint not null, user_id bigint, status enum ('CANCEL','ORDER'), primary key (order_id)) engine=InnoDB
create table orders_seq (next_val bigint) engine=InnoDB
insert into orders_seq values ( 1 )
create table product (stock integer not null, id bigint not null auto_increment, order_id bigint, name varchar(255) not null, primary key (id)) engine=InnoDB
create table user (age integer not null, id bigint not null auto_increment, email varchar(255) not null, username varchar(255) not null, primary key (id)) engine=InnoDB

INSERT INTO user(username, age, email) VALUES ('admin', 12, 'admin@example.com');
INSERT INTO user(username, age, email) VALUES ('eddy', 12, 'ktest92@gmail.com');

INSERT INTO product(name, count) VALUES ('사과', 12);
INSERT INTO product(name, count) VALUES ('수박', 33);