drop table if exists learner CASCADE
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table learner (id bigint not null, name varchar(255), primary key (id))