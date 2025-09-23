CREATE TABLE orders
(
    id         BIGSERIAL PRIMARY KEY,
    product    VARCHAR(255) NOT NULL,
    quantity   INTEGER     NOT NULL,
    status     VARCHAR(32) NOT NULL,
    created_at TIMESTAMP    NOT NULL
);


ALTER TABLE orders
    ADD CONSTRAINT chk_orders_quantity_positive CHECK (quantity > 0);