package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

public record CategoryEntity(
        UUID id,
        String name,
        int color) {
    public CategoryEntity(
            UUID id,
            String name,
            Color colour
    ) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");;
        this.color = colour.getRGB();
    }

}
