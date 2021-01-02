drop table if exists USER;
create table USER (
UUID varchar(32) not null primary key,
USERNAME varchar(20) not null,
PASSWORD varchar(100) not null
);