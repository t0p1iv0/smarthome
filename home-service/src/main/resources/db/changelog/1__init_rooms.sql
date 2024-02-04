--liquibase formatted sql

--changeset a.makseev:1 endDelimiter:/

CREATE TABLE  IF NOT EXISTS homes
(
    id                      serial primary key,
    owner_id                int not null,
    name                    text not null,
    address                 text
);

CREATE TABLE IF NOT EXISTS rooms
(
    id                      serial primary key,
    home_id                 int references homes(id),
    name                    text not null
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
    id         serial primary key,
    topic      text not null,
    message    text not null
);

CREATE INDEX IF NOT EXISTS idx_homes_owner_id ON homes (owner_id);
CREATE INDEX IF NOT EXISTS idx_homes_id ON homes (id);

CREATE INDEX IF NOT EXISTS idx_rooms_home_id ON rooms (home_id);
CREATE INDEX IF NOT EXISTS idx_rooms_id ON rooms (id);

