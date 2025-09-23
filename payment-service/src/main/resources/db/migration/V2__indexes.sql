CREATE INDEX idx_inbox_event_topic ON inbox_event (topic);
CREATE INDEX idx_inbox_event_status ON inbox_event (status);
CREATE INDEX idx_inbox_event_created_at ON inbox_event (created_at);