-- liquibase formatted sql


CREATE TABLE shelter (
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR,
    security_phone_number VARCHAR,
    schedule VARCHAR,
    safety_measures VARCHAR,
    direction_path_file VARCHAR,
    shelter_type VARCHAR
);



CREATE TABLE request (
    id  BIGSERIAL,
    chat_id BIGINT,
    request_time TIMESTAMP,
    request_text VARCHAR,
    PRIMARY KEY(id)
);



CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    telegram_id BIGINT,
    telegram_nick VARCHAR,
    full_name VARCHAR,
    phone_number VARCHAR,
    car_number VARCHAR
);