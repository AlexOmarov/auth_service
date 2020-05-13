CREATE TABLE IF NOT EXISTS PRIVILEGE (
    id SMALLINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ACCOUNT (
    id uuid not null
        constraint account_pk
            primary key,
    email varchar(512) not null
        constraint account_unique_email
            unique,
    password varchar(512) not null,
    account_non_expired boolean default false not null,
    account_non_locked boolean default false not null,
    credentials_non_expired boolean default false not null,
    enabled boolean default false not null
);

create table role
(
    id smallint AUTO_INCREMENT not null
        constraint role_pk
            primary key,
    name varchar(255) not null
);



