CREATE TABLE IF NOT EXISTS images (
    image_id          character varying(64)    NOT NULL,
    url               character varying(128)   NOT NULL,
    inverted_url               character varying(128),
    date_created       timestamp with time zone,
    mime_type       character varying(128),
    file_name           character varying(128) NOT NULL,
    CONSTRAINT library_pk PRIMARY KEY (image_id)
);