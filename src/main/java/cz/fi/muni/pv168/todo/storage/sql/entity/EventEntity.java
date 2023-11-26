package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representation of Department entity in a SQL database.
 */
public record EventEntity(UUID id,
                          String name,
                          UUID categoryId,
                          LocalDate date,
                          LocalTime startTime,
                          LocalTime endTime,

                          UUID durationId,
                          String description) {
    public EventEntity(
            UUID id,
            String name,
            UUID categoryId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            UUID durationId,
            String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");
        this.categoryId = categoryId;
        this.date = Objects.requireNonNull(date, "date must not be null");
        this.startTime = Objects.requireNonNull(startTime, "start time must not be null");
        this.endTime = Objects.requireNonNull(endTime, "end time must not be null");
        this.durationId = durationId;
        this.description = description;
    }

    public EventEntity(
            String name,
            UUID categoryId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            UUID durationId,
            String description) {
        this(null, name, categoryId, date, startTime, endTime, durationId, description);
    }
}
