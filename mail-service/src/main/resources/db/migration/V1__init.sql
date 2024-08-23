create table mail_details
(
    id      uuid primary key,
    subject varchar(255),
    body    text,
    date    timestamp    not null,
    status  varchar(255) not null
);

create table mail_receiver
(
    mail_details_id uuid,
    mail_receiver   varchar(255),
    foreign key (mail_details_id) references mail_details (id)
);

create table mail_copy
(
    mail_details_id uuid,
    mail_copy       varchar(255),
    foreign key (mail_details_id) references mail_details (id)
);

create table mail_hidden_copy
(
    mail_details_id  uuid,
    mail_hidden_copy varchar(255),
    foreign key (mail_details_id) references mail_details (id)
);