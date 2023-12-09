package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representation of Department entity in a SQL database.
 */
public record TemplateEntity(String id,
                             String name,
                             String categoryId,
                             LocalTime startTime,
                             String timeUnitId,
                             int timeUnitCount,
                             String description) {
    public TemplateEntity(
            String id,
            String name,
            String categoryId,
            LocalTime startTime,
            String timeUnitId,
            int timeUnitCount,
            String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");
        this.categoryId = categoryId;
        this.startTime = Objects.requireNonNull(startTime, "start time must not be null");
        this.timeUnitId = timeUnitId;
        this.timeUnitCount = timeUnitCount;
        this.description = description;
    }

    public TemplateEntity(
            String name,
            String categoryId,
            LocalTime startTime,
            String timeUnitId,
            int timeUnitCount,
            String description) {
        this(null, name, categoryId, startTime, timeUnitId, timeUnitCount, description);
    }
}
