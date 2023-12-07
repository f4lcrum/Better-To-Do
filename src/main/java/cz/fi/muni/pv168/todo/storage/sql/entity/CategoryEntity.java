package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.awt.Color;
import java.util.Objects;

public record CategoryEntity(
        String id,
        String name,
        int color) {
    public CategoryEntity(
            String id,
            String name,
            int color
    ) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Name must not be null");;
        this.color = color;
    }

}
