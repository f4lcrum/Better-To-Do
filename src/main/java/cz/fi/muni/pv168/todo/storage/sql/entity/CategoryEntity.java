package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.util.Objects;

public record CategoryEntity(
        String id,
        boolean isDefault,
        String name,
        int color
) {
    public CategoryEntity(
            String id,
            boolean isDefault,
            String name,
            int color
    ) {
        this.id = id;
        this.isDefault = isDefault;
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.color = color;
    }

}
