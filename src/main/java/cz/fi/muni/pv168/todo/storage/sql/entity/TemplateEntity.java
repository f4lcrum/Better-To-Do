package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of Department entity in a SQL database.
 */
public record TemplateEntity(Long id,
                             String name,
                             long categoryId,
                             LocalDate startTime,
                             long durationId,
                             String description) {
    public TemplateEntity(
            Long id,
            String name,
            long categoryId,
            LocalDate startTime,
            long durationId,
            String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");
        this.categoryId = categoryId;
        this.startTime = Objects.requireNonNull(startTime, "start time must not be null");
        this.durationId = durationId;
        this.description = Objects.requireNonNull(name, "name must not be null");

    }
}
