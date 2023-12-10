package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Representation of Department entity in a SQL database.
 */
public record TemplateEntity(String id,
                             String name,
                             String eventName,
                             String categoryId,
                             LocalTime startTime,
                             String timeUnitId,
                             int timeUnitCount,
                             String description) {
    public TemplateEntity(
            String id,
            String name,
            String eventName,
            String categoryId,
            LocalTime startTime,
            String timeUnitId,
            int timeUnitCount,
            String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.eventName = Objects.requireNonNull(eventName, "event name must not be null");
        this.categoryId = categoryId;
        this.startTime = Objects.requireNonNull(startTime, "start time must not be null");
        this.timeUnitId = timeUnitId;
        this.timeUnitCount = timeUnitCount;
        this.description = description;
    }

    public TemplateEntity(
            String name,
            String eventName,
            String categoryId,
            LocalTime startTime,
            String timeUnitId,
            int timeUnitCount,
            String description) {
        this(null, name, eventName, categoryId, startTime, timeUnitId, timeUnitCount, description);
    }
}
