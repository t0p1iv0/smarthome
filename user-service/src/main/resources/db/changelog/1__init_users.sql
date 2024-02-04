--liquibase formatted sql

--changeset a.makseev:1 endDelimiter:/

create table if not exists users
(
    id                      serial primary key,
    name                    text not null,
    username                text not null unique,
    password                text not null
);

create table if not exists refresh_tokens
(
    id                      text primary key,
    access_token_id         text not null unique,
    expires_at              integer not null
);

CREATE TABLE if not exists shedlock
(
    name       VARCHAR(64)  NOT NULL,
    lock_until TIMESTAMP    NOT NULL,
    locked_at  TIMESTAMP    NOT NULL,
    locked_by  VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE if not exists outbox_messages
(
    id       serial primary key,
    topic      text not null,
    message    text not null
);

create index if not exists idx_users_id on users (id);
create index if not exists idx_users_username on users (username);

create index if not exists idx_refresh_tokens_id on refresh_tokens (id);
create index if not exists idx_refresh_tokens_access_token_id on refresh_tokens (access_token_id);
create index if not exists idx_refresh_tokens_expires_at on refresh_tokens (expires_at);