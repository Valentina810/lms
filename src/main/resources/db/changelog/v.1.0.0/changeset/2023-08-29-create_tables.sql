CREATE TABLE IF NOT EXISTS users
(
    id_user           bigint                 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name              character varying(250) NOT NULL,
    surname           character varying(250),
    login             character varying(100) NOT NULL UNIQUE ,
    password          character varying(100) NOT NULL,
    token             character varying(100) NOT NULL,
    email             character varying(250) NOT NULL,
    date_birth        date,                            --YYYY-MM-DD
    date_registration date                   NOT NULL, --YYYY-MM-DD
    avatar_url        character varying(1000),
    CONSTRAINT user_pkey PRIMARY KEY (id_user),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS subjects
(
    id_subject bigint                 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name       character varying(300) NOT NULL,
    CONSTRAINT subject_pkey PRIMARY KEY (id_subject),
    CONSTRAINT uq_subject_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS lessons
(
    id_lesson          bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id            bigint NOT NULL,
    subject_id         bigint NOT NULL,
    date               date, --YYYY-MM-DD
    time_start         time without time zone,
    time_end           time without time zone,
    theory_url         character varying(1000),
    practice_url       character varying(1000),
    homework_url       character varying(1000),
    progress           int,  --от 0 до 100
    check_successfully boolean,
    CONSTRAINT lesson_pkey PRIMARY KEY (id_lesson),
    CONSTRAINT user_id_id_user FOREIGN KEY (user_id)
        REFERENCES users (id_user),
    CONSTRAINT subject_id_id_subject FOREIGN KEY (subject_id)
        REFERENCES subjects (id_subject)
);