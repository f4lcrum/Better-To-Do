package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Representation of Department entity in a SQL database.
 */
public record CategoryEntity(UUID id,
                             String name,
                             int r,
                             int g,
                             int b) {
    public CategoryEntity(
            UUID id,
            String name,
            int r,
            int g,
            int b) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public CategoryEntity(
            String name,
            int r,
            int g,
            int b) {
        this(null, name, r, g, b);
    }
}
