create table if not exists employees
(
    id         varchar(255) not null
        primary key,
    email      varchar(255) null,
    first_name varchar(255) null,
    last_name  varchar(255) null
)
    engine = MyISAM;

create table if not exists guests
(
    id          varchar(255) not null
        primary key,
    status      varchar(255) null,
    employee_id varchar(255) null
)
    engine = MyISAM;

create index FK3ouvoqfbb7sbp3uk6p6bl0e1l
    on guests (employee_id);

create table if not exists meetups
(
    id                    varchar(255) not null
        primary key,
    date                  datetime     null,
    location              varchar(255) null,
    organizer_employee_id varchar(255) null
)
    engine = MyISAM;

create index FKf9bkdskcvp3tle0qu54hmssop
    on meetups (organizer_employee_id);

create table if not exists meetups_guests
(
    Meetup_id varchar(255) not null,
    guests_id varchar(255) not null,
    primary key (Meetup_id, guests_id),
    constraint UK_pmu9w85ac5jc5htdplxs9c25q
        unique (guests_id)
)
    engine = MyISAM;

create table if not exists users
(
    id          varchar(255) not null
        primary key,
    password    varchar(255) null,
    rol         varchar(255) null,
    employee_id varchar(255) null
)
    engine = MyISAM;

create index FK6p2ib82uai0pj9yk1iassppgq
    on users (employee_id);

