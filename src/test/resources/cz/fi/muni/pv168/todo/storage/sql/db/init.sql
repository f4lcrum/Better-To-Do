--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`            CHAR(36) PRIMARY KEY,
    `isDefault`     BOOLEAN NOT NULL,
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

-- Insert statements for TimeUnit
INSERT INTO "TimeUnit" (`id`, `isDefault`, `name`, `hours`, `minutes`)
SELECT '4cdddc6d-bbb9-4940-8bbe-806a89f9b12b', true, 'Minute', 0, 1
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Minute'
);

INSERT INTO "TimeUnit" (`id`, `isDefault`, `name`, `hours`, `minutes`)
SELECT 'fc93f72d-aa2d-4b68-8738-8b47a4fc2a29', false, 'Hour', 1, 60
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Hour'
);

INSERT INTO "TimeUnit" (`id`, `isDefault`, `name`, `hours`, `minutes`)
SELECT 'e6d73666-b8af-4e4b-a24a-2499f5fac59f', true, 'Day', 24, 1440
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Day'
);

INSERT INTO "TimeUnit" (`id`, `isDefault`, `name`, `hours`, `minutes`)
SELECT '63aafc1a-641f-4593-927b-a3d8afb74698', false, 'Week', 168, 10080
WHERE NOT EXISTS (
    SELECT 1 FROM "TimeUnit" WHERE `name` = 'Week'
);

-- Insert statements for Category
INSERT INTO "Category" ("id", "name", "color")
VALUES
    ('158d4716-eec2-4e03-bc4c-450e31d5d38a', 'Work', -65536),
    ('d6b0d956-bbf4-4641-afdf-63c9b4127201', 'Personal', -16711936),
    ('e2e76751-b5be-4955-87e4-48f2403cd4ae', 'Family', -16776961);

-- Insert statements for Template
INSERT INTO "Template" ("id", "name", "eventName", "category", "startTime", "timeUnit", "duration", "description")
VALUES
    ('7ee351e5-a704-4685-9b02-017d28835ffb', 'Work Template', 'Work Task', '158d4716-eec2-4e03-bc4c-450e31d5d38a', '08:00:00', 'e6d73666-b8af-4e4b-a24a-2499f5fac59f', 8, 'Template for work tasks'),
    ('703115ca-468d-4090-980a-8881fb52c995', 'Personal Template', 'Personal Task', 'd6b0d956-bbf4-4641-afdf-63c9b4127201', '10:00:00', '63aafc1a-641f-4593-927b-a3d8afb74698', 30, 'Template for personal tasks'),
    ('74b62cbc-df9b-4b75-a1f6-3ba5f708ff96', 'Family Template', 'Family Gathering', 'e2e76751-b5be-4955-87e4-48f2403cd4ae', '17:00:00', '63aafc1a-641f-4593-927b-a3d8afb74698', 1, 'Template for family gatherings');

-- Insert data into Event table
INSERT INTO "Event" ("id", "name", "category", "timeUnit", "duration", "startDate", "startTime", "description")
VALUES
    ('365d67f6-5c6a-43a6-ace4-8b53ba5dc513', 'Meeting', '158d4716-eec2-4e03-bc4c-450e31d5d38a', 'e6d73666-b8af-4e4b-a24a-2499f5fac59f', 2, '2023-01-10', '09:00:00', 'Team meeting'),
    ('c9813b91-b7b6-4dfa-9e0d-ad206a7f610c', 'Gym', '158d4716-eec2-4e03-bc4c-450e31d5d38a', 'e6d73666-b8af-4e4b-a24a-2499f5fac59f', 60, '2023-01-12', '17:30:00', 'Workout session'),
    ('3fa1f956-40bf-49e7-8d12-48ed3498fe0d', 'Family Dinner', 'd6b0d956-bbf4-4641-afdf-63c9b4127201', '63aafc1a-641f-4593-927b-a3d8afb74698', 1, '2023-01-15', '18:00:00', 'Dinner with family');
