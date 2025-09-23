CREATE TABLE inbox_event
(
    id             VARCHAR(255) PRIMARY KEY,
    product        VARCHAR(255) NOT NULL,
    quantity       INT          NOT NULL,
    status         VARCHAR(50)  NOT NULL,
    topic          VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    CONSTRAINT check_inbox_event_quantity CHECK (quantity >= 0)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;