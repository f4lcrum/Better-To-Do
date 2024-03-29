
package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Representation of Event entity in a SQL database.
 */
public record EventEntity(String id,
                          String name,
                          String categoryId,
                          LocalDate date,
                          LocalTime startTime,
                          String timeUnitId,
                          int duration,
                          String description) {
    public EventEntity(
            String id,
            String name,
            String categoryId,
            LocalDate date,
            LocalTime startTime,
            String timeUnitId,
            int duration,
            String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");
        this.categoryId = categoryId;
        this.date = Objects.requireNonNull(date, "date must not be null");
        this.startTime = Objects.requireNonNull(startTime, "start time must not be null");
        this.timeUnitId = timeUnitId;
        this.duration = duration;
        this.description = description;
    }

}
