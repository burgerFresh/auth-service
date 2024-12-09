create schema if not exists public;

create table if not exists client_role_group
(
    client_role_group_id   bigserial primary key,
    client_role_group_name varchar(30) not null unique,
    is_actual              boolean     not null default true
);

create table if not exists client_role
(
    client_role_id       bigserial primary key,
    client_role_name     varchar(30) not null unique,
    client_role_group_id bigint      not null unique
        constraint fk_client_role_group
            references client_role_group,
    is_actual            boolean     not null default true
);

create table if not exists client_security_details
(
    client_security_details_id bigserial primary key,
    client_uuid                uuid         not null unique,
    login                      varchar(20)  not null unique,
    password                   varchar(100) not null,
    deleted_date               date
);

create table if not exists role_group_security
(
    role_group_security_id     bigserial primary key,
    client_security_details_id bigint not null
        constraint fk_client_security_details
            references client_security_details,
    client_role_group_id       bigint not null
        constraint fk_client_role_group
            references client_role_group
);