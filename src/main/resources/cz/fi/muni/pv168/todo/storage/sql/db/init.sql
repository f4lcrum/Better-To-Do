--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`            CHAR(36) PRIMARY KEY,
    `name`          VARCHAR(128) NOT NULL,
    `color`         INT NOT NULL
);

--
-- TimeUnit table definition
--
CREATE TABLE IF NOT EXISTS "TimeUnit"
(
    `id`            CHAR(36) PRIMARY KEY,
    `isDefault`     BOOLEAN,
    `name`          VARCHAR(128) NOT NULL,
    `hours`         INT NOT NULL,
    `minutes`       INT NOT NULL
);

--
-- Event table definition
--
CREATE TABLE IF NOT EXISTS "Event"
(
    `id`            CHAR(36) PRIMARY KEY,
    `name`          VARCHAR(128) NOT NULL,
    `category`      CHAR(36) REFERENCES "Category"(`id`),
    `timeUnit`      CHAR(36) REFERENCES "TimeUnit"(`id`),
    `duration`      INT NOT NULL,
    `startDate`     DATE NOT NULL,
    `startTime`     TIME NOT NULL,
    `description`   VARCHAR(1024) NOT NULL
);

--
-- Template table definition
--
CREATE TABLE IF NOT EXISTS "Template"
(
    `id`            CHAR(36) PRIMARY KEY,
    `name`          VARCHAR(128) NOT NULL,
    `eventName`     VARCHAR(128) NOT NULL,
    `category`      CHAR(36) REFERENCES "Category"(`id`),
    `startTime`     TIME NOT NULL,
    `timeUnit`      CHAR(36) REFERENCES "TimeUnit"(`id`),
    `duration`      INT NOT NULL,
    `description`   VARCHAR(1024) NOT NULL
);
