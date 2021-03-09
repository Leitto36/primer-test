CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE card
(
    id               UUID        NOT NULL DEFAULT uuid_generate_v4(),
    merchant_id      UUID        NOT NULL,
    card_holder_name TEXT        NOT NULL,
    number           TEXT        NOT NULL,
    expiration_month SMALLINT    NOT NULL,
    expiration_year  SMALLINT    NOT NULL,
    cvv              SMALLINT    NOT NULL,
    created          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT card_pkey PRIMARY KEY (id),
    CONSTRAINT card_number UNIQUE (number)
);

CREATE INDEX card_token_merchant_id_idx
    ON public.card (merchant_id);
