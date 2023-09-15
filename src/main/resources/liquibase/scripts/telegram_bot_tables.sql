-- liquibase formatted sql

-- changeset grigorii:create-animal_owner-table
CREATE TABLE animal_owner (
    id                  serial PRIMARY KEY,
    id_chat             bigint,
    registered          boolean,
    contact_information text,
    dog_lover           boolean,
    took_the_animal     boolean,
    can_save_contact    boolean,
    be_volunteer        boolean,
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

-- changeset grigorii:create-animal-table
CREATE TABLE animal (
    id                  serial PRIMARY KEY,
    name                text,
    file_path           text,
    file_size           bigint,
    media_type          text,
    data                oid,
    shelter_id          int4,
    animal_owner_id     int4
);