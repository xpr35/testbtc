CREATE TABLE public.record
(
    id       BIGINT PRIMARY KEY                     NOT NULL,
    datetime timestamp with time zone DEFAULT now() NOT NULL,
    amount   NUMERIC                                NOT NULL
);

CREATE
UNIQUE INDEX datetime_index
    ON record (datetime)
    WHERE datetime IS NOT NULL;

CREATE SEQUENCE RECORD_MESSAGE_SEQ
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE CACHE 10;