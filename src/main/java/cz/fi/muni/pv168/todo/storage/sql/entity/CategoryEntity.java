package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.util.Objects;

public record CategoryEntity(
        String id,
        String name,
        int r,
        int g,
        int b
) {
    public CategoryEntity(
            String id,
            String name,
            int r,
            int g,
            int b
    ) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.r = r;
        this.g = g;
        this.b = b;
    }

}
