CREATE TABLE gira_events
(
    id              UUID                        NOT NULL,
    name            VARCHAR(255)                NOT NULL,
    started_at      time WITHOUT TIME ZONE      NOT NULL,
    visible_in_card BOOLEAN                     NOT NULL,
    description     VARCHAR(255),
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_gira_events PRIMARY KEY (id)
);

CREATE TABLE giras
(
    id          UUID                        NOT NULL,
    date        date                        NOT NULL,
    theme_id    UUID,
    period      VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    year        INTEGER                     NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_giras PRIMARY KEY (id)
);

CREATE TABLE giras_events
(
    event_id UUID NOT NULL,
    gira_id  UUID NOT NULL
);

CREATE TABLE theme
(
    id          UUID                        NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_theme PRIMARY KEY (id)
);

ALTER TABLE giras
    ADD CONSTRAINT FK_GIRAS_ON_THEME FOREIGN KEY (theme_id) REFERENCES theme (id);

ALTER TABLE giras_events
    ADD CONSTRAINT fk_gireve_on_gira_entity FOREIGN KEY (gira_id) REFERENCES giras (id);

ALTER TABLE giras_events
    ADD CONSTRAINT fk_gireve_on_gira_event_entity FOREIGN KEY (event_id) REFERENCES gira_events (id);
