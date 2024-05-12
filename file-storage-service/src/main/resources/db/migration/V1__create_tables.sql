create table file_meta_info
(
    id               uuid primary key,
    owner_id         uuid         not null,
    size             float8       not null,
    name             varchar(255) not null,
    bucket           varchar(255) not null,
    upload_date_time timestamp    not null
);
