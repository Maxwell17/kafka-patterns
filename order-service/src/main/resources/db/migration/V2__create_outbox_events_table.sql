CREATE TABLE outbox_events
(
    id             BIGSERIAL PRIMARY KEY,
    aggregate_type text        NOT NULL,
    aggregate_id   text        NOT NULL,
    topic          text        NOT NULL,
    payload        JSONB       NOT NULL,
    published      boolean     NOT NULL DEFAULT FALSE,
    created_at     timestamptz NOT NULL DEFAULT now()
);