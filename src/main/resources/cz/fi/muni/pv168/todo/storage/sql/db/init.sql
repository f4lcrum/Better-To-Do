--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`           CHAR(36) ALWAYS AS IDENTITY PRIMARY KEY,
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
    `id`           CHAR(36) ALWAYS AS IDENTITY PRIMARY KEY,
    `name`         VARCHAR(128) NOT NULL,
    `hourCount`    INT NOT NULL,
    `minuteCount`  INT NOT NULL
);

--
-- Event table definition
--
CREATE TABLE IF NOT EXISTS "Event"
(
    `id`            CHAR(36) ALWAYS AS IDENTITY PRIMARY KEY,
    `name`          VARCHAR(128) NOT NULL,
    `category`      CHAR(36) REFERENCES "Category"(`id`),
    `timeUnit`      CHAR(36) REFERENCES "TimeUnit"(`id`),
    `timeUnitCount` INT NOT NULL,
    `startDate`     DATE NOT NULL,
    `startTime`     TIME NOT NULL,
    `description`   VARCHAR(1024) NOT NULL
);

--
-- Template table definition
--
CREATE TABLE IF NOT EXISTS "Template"
(
    `id`           CHAR(36) ALWAYS AS IDENTITY PRIMARY KEY,
    `name`         VARCHAR(128) NOT NULL,
    `category`     CHAR(36) REFERENCES "Category"(`id`),
    `timeUnit`      CHAR(36) REFERENCES "TimeUnit"(`id`),
    `timeUnitCount` INT NOT NULL,
    `startTime`    TIME NOT NULL,
    `description`  VARCHAR(1024) NOT NULL
);
