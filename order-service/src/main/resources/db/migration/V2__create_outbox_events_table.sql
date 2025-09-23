CREATE TABLE outbox_events
(
    id             BIGSERIAL PRIMARY KEY,
    aggregate_type VARCHAR(255) NOT NULL,
    aggregate_id   VARCHAR(255) NOT NULL,
    topic          VARCHAR(255) NOT NULL,
    payload        JSONB       NOT NULL,
    published      BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP    NOT NULL
);