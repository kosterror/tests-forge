create table subject
(
    id         uuid primary key,
    name       varchar(255) not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);

create table test_pattern
(
    id          uuid primary key,
    subject_id  uuid         references subject on delete set null,
    owner_id    uuid         not null,
    name        varchar(255) not null,
    description varchar(255),
    created_at  timestamp(6) not null,
    updated_at  timestamp(6) not null
);

create table test_published
(
    id                      uuid primary key,
    test_pattern_id         uuid         not null references test_pattern on delete cascade,
    timer                   integer,
    deadline                timestamp(6) not null,
    show_points_to_students boolean      not null,
    is_need_post_moderation boolean      not null,
    created_at              timestamp(6) not null,
    updated_at              timestamp(6) not null
);

create table test_generated
(
    id                uuid primary key,
    published_test_id uuid         not null references test_published on delete cascade,
    user_id           uuid         not null,
    partitions        jsonb,
    points            integer,
    status            varchar(255) not null,
    submit_date_time  timestamp(6),
    created_at        timestamp(6) not null,
    updated_at        timestamp(6) not null
);

create table partition
(
    id              uuid primary key,
    test_pattern_id uuid         not null references test_pattern on delete cascade,
    "order"         integer      not null,
    name            varchar(255) not null,
    description     varchar(255),
    created_at      timestamp(6) not null,
    updated_at      timestamp(6) not null
);

create table block
(
    id           uuid primary key,
    partition_id uuid         not null references partition on delete cascade,
    type         varchar(255) not null,
    name         varchar(255) not null,
    description  varchar(255),
    "order"      integer,
    created_at   timestamp(6) not null,
    updated_at   timestamp(6) not null
);

create table dynamic_block
(
    id             uuid primary key references block on delete cascade,
    question_count integer not null
);

create table static_block
(
    id uuid primary key references block on delete cascade
);

create table group_id
(
    group_id          uuid,
    test_published_id uuid not null references test_published on delete cascade
);

create table user_id
(
    test_published_id uuid not null references test_published on delete cascade,
    user_id           uuid
);

create table variant
(
    id          uuid primary key,
    block_id    uuid         not null references static_block on delete cascade,
    name        varchar(255) not null,
    description varchar(255),
    created_at  timestamp(6) not null,
    updated_at  timestamp(6) not null
);

create table question
(
    id                    uuid primary key,
    dynamic_block_id      uuid references dynamic_block on delete cascade,
    subject_id            uuid references subject on delete cascade,
    variant_id            uuid references variant on delete cascade,
    type                  varchar(255) not null,
    name                  varchar(255) not null,
    owner_id              uuid,
    "order"               integer,
    is_question_from_bank boolean      not null default false,
    created_at            timestamp(6) not null,
    updated_at            timestamp(6) not null,
    check ( is_question_from_bank and subject_id is not null and variant_id is null and dynamic_block_id is null or
            not is_question_from_bank and subject_id is null and variant_id is not null and dynamic_block_id is null or
            not is_question_from_bank and subject_id is null and variant_id is null and dynamic_block_id is not null)
);

create table matching_question
(
    id uuid primary key references question on delete cascade
);

create table definition
(
    id          uuid primary key,
    question_id uuid         not null references matching_question on delete cascade,
    text        varchar(255) not null,
    created_at  timestamp(6) not null,
    updated_at  timestamp(6) not null
);

create table term
(
    id            uuid primary key,
    question_id   uuid         not null references matching_question on delete cascade,
    definition_id uuid         not null references definition on delete cascade,
    text          varchar(255) not null,
    created_at    timestamp(6) not null,
    updated_at    timestamp(6) not null
);

create table matching_question_points
(
    points      integer not null,
    points_key  integer not null,
    question_id uuid    not null references matching_question on delete cascade,
    primary key (points_key, question_id)
);

create table multiple_choice_question
(
    id uuid primary key references question on delete cascade
);

create table multiple_option
(
    id          uuid primary key,
    question_id uuid         not null references multiple_choice_question on delete cascade,
    is_right    boolean      not null,
    "order"     integer      not null,
    name        varchar(255) not null,
    created_at  timestamp(6) not null,
    updated_at  timestamp(6) not null
);

create table multiple_question_points
(
    points      integer not null,
    points_key  integer not null,
    question_id uuid    not null references multiple_choice_question on delete cascade,
    primary key (points_key, question_id)
);

create table question_attachment
(
    question_id uuid not null references question on delete cascade,
    attachments uuid not null
);

create table single_choice_question
(
    id     uuid primary key references question on delete cascade,
    points integer not null
);

create table single_option
(
    id          uuid primary key,
    question_id uuid         not null references single_choice_question on delete cascade,
    name        varchar(255) not null,
    is_right    boolean      not null,
    "order"     integer,
    created_at  timestamp(6) not null,
    updated_at  timestamp(6) not null
);

create table text_input_question
(
    id                uuid primary key references question on delete cascade,
    is_case_sensitive boolean not null,
    points            integer not null
);

create table text_input_answer_question
(
    question_id uuid         not null references text_input_question on delete cascade,
    answer      varchar(255) not null
);

