create sequence hibernate_sequence start 1 increment 1

create table item (
    id int8 not null,
    cost float4,
    description varchar(255),
    dislocation varchar(255),
    image oid,
    is_deleted boolean,
    name varchar(255),
    size float4,
    user_id int8 not null,
    type_id int8 not null,
    primary key (id))
create table roles (
    user_id int8 not null,
    roles varchar(255))
create table type (
    id int8 not null,
    name varchar(255),
    primary key (id))
create table users (
    id int8 not null,
    active boolean,
    password varchar(255),
    username varchar(255),
    primary key (id))

alter table if exists item
    add constraint FK_item_users foreign key (user_id) references users
alter table if exists item
    add constraint FK_item_type foreign key (type_id) references type
alter table if exists roles
    add constraint FK_roles_users foreign key (user_id) references users