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