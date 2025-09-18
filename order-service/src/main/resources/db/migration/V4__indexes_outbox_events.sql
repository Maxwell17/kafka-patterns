CREATE INDEX idx_outbox_events_published_created_at ON outbox_events (published, created_at);
CREATE INDEX idx_outbox_events_aggregate ON outbox_events (aggregate_type, aggregate_id);
CREATE INDEX idx_outbox_events_topic ON outbox_events (topic);