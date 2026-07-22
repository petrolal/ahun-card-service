CREATE TABLE duties
(
    id          UUID         NOT NULL,
    date        date         NOT NULL,
    theme_id    UUID,
    duty_type   VARCHAR(255) NOT NULL,
    period VARCHAR (255) NOT NULL,
    description VARCHAR(255),
    year        INTEGER      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_duties PRIMARY KEY (id)
);

CREATE TABLE duties_events
(
    duty_id  UUID NOT NULL,
    event_id UUID NOT NULL
);

CREATE TABLE duty_events
(
    id              UUID         NOT NULL,
    name            VARCHAR(255) NOT NULL,
    started_at      time WITHOUT TIME ZONE      NOT NULL,
    visible_in_card BOOLEAN      NOT NULL,
    description     VARCHAR(255),
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_duty_events PRIMARY KEY (id)
);

CREATE TABLE theme
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_theme PRIMARY KEY (id)
);

ALTER TABLE duties
    ADD CONSTRAINT FK_DUTIES_ON_THEME FOREIGN KEY (theme_id) REFERENCES theme (id);

ALTER TABLE duties_events
    ADD CONSTRAINT fk_duteve_on_duty_entity FOREIGN KEY (duty_id) REFERENCES duties (id);

ALTER TABLE duties_events
    ADD CONSTRAINT fk_duteve_on_duty_event_entity FOREIGN KEY (event_id) REFERENCES duty_events (id);
