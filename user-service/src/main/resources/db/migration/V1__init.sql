create table person
(
    id         uuid primary key,
    email      varchar(255) not null unique,
    name       varchar(255) not null,
    password   varchar(512) not null,
    patronymic varchar(255),
    role       varchar(32)  not null,
    surname    varchar(255) not null
);

create table refresh_tokens
(
    id           uuid primary key,
    expired_date timestamp     not null,
    issued_date  timestamp     not null,
    owner_id     uuid references person on delete cascade,
    token        varchar(1024) not null
);
