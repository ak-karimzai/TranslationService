CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE translation_info(
    id uuid primary key default gen_random_uuid(),
    source_text varchar not null,
    translated_text varchar not null,
    user_ip varchar not null,
    created_at timestamp default now()
);