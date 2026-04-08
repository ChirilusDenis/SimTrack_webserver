SET search_path = project, pg_catalog;

CREATE TABLE organizer_applications (
    id UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    username TEXT,
    password TEXT,
    email TEXT,

    PRIMARY KEY (id)
);