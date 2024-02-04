--liquibase formatted sql

--changeset a.makseev:2 endDelimiter:/

CREATE TYPE category AS ENUM ('LIGHT');

create table if not exists devices
(
    id                      serial primary key,
    tuya_id                 text not null unique,
    owner_id                int not null,
    name                    text not null,
    home_id                 int not null,
    room_id                 int,
    category                category not null
);

create index if not exists idx_devices_id on devices (id);
create index if not exists idx_tuya_id on devices (tuya_id);
create index if not exists idx_owner_id on devices (owner_id);
create index if not exists idx_home_id on devices (home_id);
create index if not exists idx_room_id on devices (room_id);
