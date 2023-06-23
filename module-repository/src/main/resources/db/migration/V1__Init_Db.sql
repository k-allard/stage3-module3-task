CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE authors
(
    id               BIGINT NOT NULL,
    name             VARCHAR(255),
    create_date      TIMESTAMP WITHOUT TIME ZONE,
    last_update_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_authors PRIMARY KEY (id)
);