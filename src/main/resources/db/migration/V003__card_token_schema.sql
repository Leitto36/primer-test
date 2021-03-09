CREATE TABLE card_token
(
    id                UUID        NOT NULL DEFAULT uuid_generate_v4(),
    card_id           UUID        NOT NULL,
    processor_type_id SMALLINT        NOT NULL,
    token             TEXT        NOT NULL,
    status_id         SMALLINT    NOT NULL,
    created           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT card_token_pkey PRIMARY KEY (id),
    CONSTRAINT card_token_token UNIQUE (token)
);

CREATE INDEX card_token_token_idx
    ON public.card (token);
