CREATE TABLE orders
(
    id         BIGSERIAL PRIMARY KEY,
    product    text        NOT NULL,
    quantity   integer     NOT NULL,
    status     varchar(32) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);


ALTER TABLE orders
    ADD CONSTRAINT chk_orders_quantity_positive CHECK (quantity > 0);