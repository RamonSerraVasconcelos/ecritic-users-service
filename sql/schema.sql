CREATE TABLE users
(
    id                  UUID NOT NULL,
    name                TEXT NOT NULL,
    email               TEXT NOT NULL,
    password            TEXT NOT NULL,
    phone               TEXT,
    description         TEXT,
    country_id          INT,
    profile_picture_id  TEXT,
    role                TEXT,
    active              BOOLEAN,
    email_reset_date    TIMESTAMP,
    email_reset_hash    TEXT,
    new_email_reset     TEXT,
    password_reset_date TIMESTAMP,
    password_reset_hash TEXT,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE countries
(
    id   BIGSERIAL NOT NULL,
    name TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE addresses
(
    id           UUID       NOT NULL,
    country_id   INT       NOT NULL,
    uf           CHAR(2)    NOT NULL,
    city         TEXT       NOT NULL,
    neighborhood TEXT       NOT NULL,
    street       TEXT       NOT NULL,
    postal_code  VARCHAR(9) NOT NULL,
    complement   TEXT,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE user_adresses
(
    user_id    UUID NOT NULL,
    address_id UUID NOT NULL,
    is_default BOOLEAN,
    PRIMARY KEY (user_id, address_id)
);

CREATE TABLE banlist
(
    id         BIGSERIAL      NOT NULL,
    user_id    UUID,
    action     VARCHAR(5),
    motive     VARCHAR(1000),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS users ADD CONSTRAINT users_unique_email UNIQUE (email);

ALTER TABLE IF EXISTS users ADD CONSTRAINT fk_users_countries_id FOREIGN KEY (country_id) REFERENCES countries;

ALTER TABLE IF EXISTS addresses ADD CONSTRAINT fk_addresses_countries_id FOREIGN KEY (country_id) REFERENCES countries;

ALTER TABLE IF EXISTS user_adresses ADD CONSTRAINT fk_user_adresses_users_id FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS user_adresses ADD CONSTRAINT fk_user_adresses_addresses_id FOREIGN KEY (address_id) REFERENCES addresses;

ALTER TABLE IF EXISTS banlist ADD CONSTRAINT fk_banlist_users_id FOREIGN KEY (user_id) REFERENCES users;

-- FUNCTION TO CREATE AND UPDATE DATES
CREATE
OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS
'
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
'
LANGUAGE plpgsql;

--TRIGGERS TO UPDATE DATES ON TABLES
CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON users
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON addresses
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();