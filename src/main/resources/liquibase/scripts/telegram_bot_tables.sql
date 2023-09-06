-- liquibase formatted sql

-- changeset aburmistrov:1
CREATE TABLE cat_shelter
(
    id            serial PRIMARY KEY,
    name          varchar(255),
    about_shelter varchar(255),
    location      varchar(255),
    security      varchar(255), --контактные данные охраны для оформления пропуска на машину
    safety_advice varchar(255), --техника безопасности на территории приюта
    timetable     varchar(255)  --расписание работы приюта
);

CREATE TABLE dog_shelter
(
    id            serial PRIMARY KEY,
    name          varchar(255),
    about_shelter varchar(255),
    location      varchar(255),
    security      varchar(255), --контактные данные охраны для оформления пропуска на машину
    safety_advice varchar(255), --техника безопасности на территории приюта
    timetable     varchar(255)  --расписание работы приюта
)

-- changeset grigorii:create-animal_owner-table
CREATE TABLE animal_owner (
    id                  serial PRIMARY KEY,
    id_chat             bigint,
    contact_information text,
    stage               integer,
    dog_lover           boolean,
    took_the_animal     boolean
)