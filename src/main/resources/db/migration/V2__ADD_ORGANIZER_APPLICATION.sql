SET search_path = project, pg_catalog;

CREATE TABLE organizer_applications (
    id UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    username TEXT,
    password TEXT,
    email TEXT,
    approved_by_id UUID,

    PRIMARY KEY (id),
    FOREIGN KEY (approved_by_id) references project.users(id)
);