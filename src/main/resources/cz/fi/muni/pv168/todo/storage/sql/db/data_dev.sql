--
-- Development data - used for development
--

--  Category
INSERT INTO "Category" ("name", "r", "g", "b")
VALUES ('Work', 255, 0, 0),
       ('Personal', 0, 255, 0),
       ('Health', 0, 0, 255),
       ('Education', 255, 255, 0)
;

--  TimeUnit
INSERT INTO "TimeUnit" ("name", "hourCount", "minuteCount")
VALUES ('One Hour', 1, 0),
       ('Half Hour', 0, 30),
       ('Two Hours', 2, 0),
       ('Fifteen Minutes', 0, 15)
;

--  Event
INSERT INTO "Event"  ("name", "category", "duration", "startDateTime", "description")
VALUES ('Team Meeting', 1, 1, '2023-11-27 09:00:00', 'Weekly team meeting to discuss project progress'),
       ('Yoga Class', 3, 2, '2023-11-27 07:00:00', 'Morning yoga session for wellness'),
       ('Study Session', 4, 3, '2023-11-27 18:00:00', 'Study session for upcoming exams'),
       ('Doctor Appointment', 3, 4, '2023-11-28 10:30:00', 'Routine check-up with the doctor')
;

--  Template
INSERT INTO "Template"  ("name", "category", "startTime", "duration", "description")
VALUES ('Workout Routine', 3, '06:30:00', 60, 'Morning workout session to stay fit'),
       ('Daily Planning', 2, '08:00:00', 30, 'Planning the day ahead'),
       ('Evening Reading', 4, '20:00:00', 45, 'Reading time to relax and learn'),
       ('Weekly Review', 1, '17:00:00', 90, 'Weekly review of work tasks and goals')
;
