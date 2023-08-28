CREATE TABLE IF NOT EXISTS users
(
    id_user         bigint                 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name            character varying(250) NOT NULL,
    email           character varying(250) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id_user),
    CONSTRAINT uq_users_email UNIQUE (email)
);