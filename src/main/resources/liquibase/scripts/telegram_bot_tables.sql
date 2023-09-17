-- liquibase formatted sql

-- changeset grigorii:create-animal_owner-table
CREATE TABLE animal_owner (
    id                  serial PRIMARY KEY,
    id_chat             bigint,
    contact_information text,
    registered          boolean,
    dog_lover           boolean,
    took_the_animal     boolean,
    can_save_contact    boolean,
    be_volunteer        boolean,
    help_volunteer      boolean,
    can_send_report     boolean,
    volunteer_id        int4,
    shelter_id          int4
);

-- changeset grigorii:create-shelter-table
CREATE TABLE shelter (
    id                  serial PRIMARY KEY,
    name                text
);

-- changeset grigorii:create-volunteer-table
CREATE TABLE volunteer (
    id                  serial PRIMARY KEY,
    id_chat             bigint,
    name                text,
    is_busy             boolean,
    shelter_id          int4
);

-- changeset grigorii:create-report-table
CREATE TABLE report (
    id                  serial PRIMARY KEY,
    date                date,
    report              text,
    telegram_field_id   text,
    file_size           integer,
    binary_content_id   int4,
    shelter_id          int4,
    animal_owner_id     int4
);

-- changeset grigorii:create-binary-content-table
CREATE TABLE binary_content (
    id                  serial PRIMARY KEY,
    data                oid
);

-- changeset grigorii:create-animal-table
CREATE TABLE animal (
    id                  serial PRIMARY KEY,
    name                text,
    shelter_id          int4,
    animal_owner_id     int4
);