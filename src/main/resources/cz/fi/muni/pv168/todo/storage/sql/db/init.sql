--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`         VARCHAR(128) NOT NULL,
    `r`            SMALLINT NOT NULL,
    `g`            SMALLINT NOT NULL,
    `b`            SMALLINT NOT NULL
);

--
-- TimeUnit table definition
--
CREATE TABLE IF NOT EXISTS "TimeUnit"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`         VARCHAR(128) NOT NULL,
    `hourCount`    INT NOT NULL,
    `minuteCount`  INT NOT NULL
);

--
-- Event table definition
--
CREATE TABLE IF NOT EXISTS "Event"
(
    `id`            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`          VARCHAR(128) NOT NULL,
    `category`      BIGINT REFERENCES "Category"(`id`),
    `duration`      BIGINT REFERENCES "TimeUnit"(`id`),
    `startDateTime` DATETIME NOT NULL,
    `description`   VARCHAR(1024) NOT NULL
);

--
-- Template table definition
--
CREATE TABLE IF NOT EXISTS "Template"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`         VARCHAR(128) NOT NULL,
    `category`     BIGINT REFERENCES "Category"(`id`),
    `startTime`    TIME NOT NULL,
    `duration`     BIGINT NOT NULL,
    `description`  VARCHAR(1024) NOT NULL
);
