-- DROP TABLE Template;
-- DROP TABLE Event;
-- DROP TABLE Category;
-- DROP TABLE TimeUnit;

-- Insert data into Category table
INSERT INTO "Category" ("id", "name", "r", "g", "b")
VALUES
    ('158d4716-eec2-4e03-bc4c-450e31d5d38a', 'Work', 255, 0, 0),
    ('d6b0d956-bbf4-4641-afdf-63c9b4127201', 'Personal', 0, 128, 255),
    ('e2e76751-b5be-4955-87e4-48f2403cd4ae', 'Family', 0, 255, 0);

-- Insert data into TimeUnit table
INSERT INTO "TimeUnit" ("id", "name", "hours", "minutes")
VALUES
    ('25aae21c-74f6-425d-8b52-b00cb6b34efb', 'Hours', 1, 0),
    ('0e587bc6-83a1-44ac-a911-0bfe0df998e4', 'Minutes', 0, 1),
    ('0381a409-6d77-45d5-9dac-02c91d0d41a0', 'Days', 24, 0);

-- Insert data into Event table
INSERT INTO "Event" ("id", "name", "category", "timeUnit", "duration", "startDate", "startTime", "description")
VALUES
    ('365d67f6-5c6a-43a6-ace4-8b53ba5dc513', 'Meeting', '158d4716-eec2-4e03-bc4c-450e31d5d38a', '25aae21c-74f6-425d-8b52-b00cb6b34efb', 2, '2023-01-10', '09:00:00', 'Team meeting'),
    ('c9813b91-b7b6-4dfa-9e0d-ad206a7f610c', 'Gym', 'd6b0d956-bbf4-4641-afdf-63c9b4127201', '0e587bc6-83a1-44ac-a911-0bfe0df998e4', 60, '2023-01-12', '17:30:00', 'Workout session'),
    ('3fa1f956-40bf-49e7-8d12-48ed3498fe0d', 'Family Dinner', 'e2e76751-b5be-4955-87e4-48f2403cd4ae', '0381a409-6d77-45d5-9dac-02c91d0d41a0', 1, '2023-01-15', '18:00:00', 'Dinner with family');

-- -- Insert data into Template table
INSERT INTO "Template" ("id", "name", "eventName", "category", "startTime", "timeUnit", "duration", "description")
VALUES
    ('7ee351e5-a704-4685-9b02-017d28835ffb', 'Work Template', 'Work Task', '158d4716-eec2-4e03-bc4c-450e31d5d38a', '08:00:00', '25aae21c-74f6-425d-8b52-b00cb6b34efb', 8, 'Template for work tasks'),
    ('703115ca-468d-4090-980a-8881fb52c995', 'Personal Template', 'Personal Task', 'd6b0d956-bbf4-4641-afdf-63c9b4127201', '10:00:00', '0e587bc6-83a1-44ac-a911-0bfe0df998e4', 30, 'Template for personal tasks'),
    ('74b62cbc-df9b-4b75-a1f6-3ba5f708ff96', 'Family Template', 'Family Gathering', 'e2e76751-b5be-4955-87e4-48f2403cd4ae', '17:00:00', '0381a409-6d77-45d5-9dac-02c91d0d41a0', 1, 'Template for family gatherings');
