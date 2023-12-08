package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Representation of Department entity in a SQL database.
 */
public record TemplateEntity(String id,
                             String name,
                             String categoryId,
                             LocalDate startTime,
                             String durationId,
                             String description) {
    public TemplateEntity(
            String id,
            String name,
            String categoryId,
            LocalDate startTime,
            String durationId,
            String description) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");
        this.categoryId = categoryId;
        this.startTime = Objects.requireNonNull(startTime, "start time must not be null");
        this.durationId = durationId;
        this.description = description;
    }

    public TemplateEntity(
            String name,
            String categoryId,
            LocalDate startTime,
            String durationId,
            String description) {
        this(null, name, categoryId, startTime, durationId, description);
    }
}