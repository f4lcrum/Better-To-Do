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
    `id`           CHAR(36) PRIMARY KEY,
    `name`         VARCHAR(128) NOT NULL,
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
    `category`      CHAR(36) REFERENCES "Category"(`id`),
    `timeUnit`      CHAR(36) REFERENCES "TimeUnit"(`id`),
    `duration`      INT NOT NULL,
    `startTime`     TIME NOT NULL,
    `description`   VARCHAR(1024) NOT NULL
);

-- Insert statements for TimeUnit
INSERT INTO "TimeUnit" (`id`, `name`, `hours`, `minutes`)
SELECT '4cdddc6d-bbb9-4940-8bbe-806a89f9b12b', 'Minute', 0, 1
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Minute'
);

INSERT INTO "TimeUnit" (`id`, `name`, `hours`, `minutes`)
SELECT 'fc93f72d-aa2d-4b68-8738-8b47a4fc2a29', 'Hour', 1, 60
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Hour'
);

INSERT INTO "TimeUnit" (`id`, `name`, `hours`, `minutes`)
SELECT 'e6d73666-b8af-4e4b-a24a-2499f5fac59f', 'Day', 24, 1440
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Day'
);

INSERT INTO "TimeUnit" (`id`, `name`, `hours`, `minutes`)
SELECT '63aafc1a-641f-4593-927b-a3d8afb74698', 'Week', 168, 10080
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Week'
);

-- Insert statements for Category
INSERT INTO "Category" ("id", "name", "color")
VALUES
    ('158d4716-eec2-4e03-bc4c-450e31d5d38a', 'Work', -65536),
    ('d6b0d956-bbf4-4641-afdf-63c9b4127201', 'Personal', -16711936),
    ('e2e76751-b5be-4955-87e4-48f2403cd4ae', 'Family', -16776961);
