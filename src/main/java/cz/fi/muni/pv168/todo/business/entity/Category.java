package cz.fi.muni.pv168.todo.business.entity;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

public class Category implements Entity {

    private final UUID id;
    private final boolean isDefault;
    private final String name;
    private final Color color;

    public Category(UUID id, String name, Color color) {
        this.id = id;
        this.isDefault = false;
        this.name = name;
        this.color = color;
    }

    @Override
    public UUID getGuid() {
        return this.id;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colour=" + color +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(category.id, this.id);

    }
}
