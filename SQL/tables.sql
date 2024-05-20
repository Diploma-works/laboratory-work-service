CREATE TABLE IF NOT EXISTS labwork
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(128) UNIQUE NOT NULL,
    description TEXT                NOT NULL
);

CREATE TABLE IF NOT EXISTS topic
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS task
(
    id          SERIAL PRIMARY KEY,
    description TEXT    NOT NULL,
    topic_id    INTEGER NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES topic (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS labworktask
(
    id         SERIAL PRIMARY KEY,
    variant    INTEGER NOT NULL,
    labwork_id INTEGER NOT NULL,
    FOREIGN KEY (labwork_id) REFERENCES labwork (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS labwork_topic
(
    topic_id   INTEGER NOT NULL,
    labwork_id INTEGER NOT NULL,
    PRIMARY KEY (topic_id, labwork_id),
    FOREIGN KEY (topic_id) REFERENCES topic (id),
    FOREIGN KEY (labwork_id) REFERENCES labwork (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS labworktask_task
(
    labworktask_id INTEGER NOT NULL,
    task_id        INTEGER NOT NULL,
    PRIMARY KEY (labworktask_id, task_id),
    FOREIGN KEY (labworktask_id) REFERENCES labworktask (id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES task (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(60)        NOT NULL,
    role     VARCHAR(16) DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS answer
(
    id             SERIAL PRIMARY KEY,
    data           BYTEA,
    filename       TEXT,
    description    VARCHAR(128),
    date_time      TIMESTAMP NOT NULL,
    labworktask_id INTEGER   NOT NULL,
    user_id        INTEGER   NOT NULL,
    FOREIGN KEY (labworktask_id) REFERENCES labworktask (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

