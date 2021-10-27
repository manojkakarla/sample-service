CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE;

CREATE TABLE user (
    id bigint NOT NULL,
    name character varying(255),
    PRIMARY KEY (id)
);

CREATE TABLE user_interest (
    id bigint NOT NULL,
    name character varying(255),
    type character varying(20),
    language character varying(40),
    region character varying(20),
    user_id bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    UNIQUE (user_id, type, language, region),
    UNIQUE (user_id, name)
);

INSERT INTO user (id, name) VALUES (1, 'User1');
INSERT INTO user (id, name) VALUES (2, 'User2');
INSERT INTO user (id, name) VALUES (3, 'User3');
