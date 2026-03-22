CREATE SCHEMA project;
SET search_path = project, pg_catalog;

CREATE TABLE users (
    id UUID NOT NULL ,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    username TEXT,
    password TEXT,
    email TEXT,
    role TEXT,

    PRIMARY KEY (id)
);

CREATE TABLE championships (
    id UUID NOT NULL ,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name TEXT,
    description TEXT,
    racing_class TEXT,
    status TEXT,
    created_by_id UUID,

    PRIMARY KEY (id),
    FOREIGN KEY (created_by_id) REFERENCES project.users(id)
);

CREATE TABLE championship_entries (
    id UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id UUID,
    championship_id UUID,
    application_status TEXT,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES project.users(id),
    FOREIGN KEY (championship_id) REFERENCES project.championships(id)
);

CREATE TABLE races (
    id UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name TEXT,
    track_name TEXT,
    championship_id UUID,

    PRIMARY KEY (id),
    FOREIGN KEY (championship_id) REFERENCES project.championships(id)
);

CREATE TABLE laps (
    id UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    time TIME,
    race_id UUID,
    driver_id UUID,
    verified_by_id UUID,

    PRIMARY KEY (id),
    FOREIGN KEY (race_id) REFERENCES project.races(id),
    FOREIGN KEY (driver_id) REFERENCES project.users(id),
    FOREIGN KEY (verified_by_id) REFERENCES project.users(id)
 );

CREATE TABLE race_submissions (
    id UUID NOT NULL ,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    time TIME,
    video_url TEXT,
    status TEXT,
    user_id UUID,
    race_id UUID,

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES project.users(id),
    FOREIGN KEY (race_id) REFERENCES project.races(id)
);
