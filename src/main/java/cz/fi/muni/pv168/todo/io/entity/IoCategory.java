package cz.fi.muni.pv168.todo.io.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = IoCategory.CategoryBuilder.class)
public class IoCategory implements IoEntity {

    private final UUID id;
    private final boolean isDefault;
    @JsonProperty
    private final String name;
    @JsonProperty
    @JsonDeserialize(contentUsing = ColorJsonDeserializer.class)
    private final Color color;

    public IoCategory(UUID id, String name, Color color) {
        this.id = id;
        this.isDefault = false;
        this.name = name;
        this.color = color;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean getIsDefault() {
        return isDefault;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IoCategory category = (IoCategory) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public String toString() {
        return "IoCategory{" +
                "id=" + id +
                ", isDefault=" + isDefault +
                ", name='" + name + '\'' +
                ", color=" + color +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class CategoryBuilder {

        private UUID id;
        private String name;
        private boolean isDefault;
        @JsonDeserialize(contentUsing = ColorJsonDeserializer.class)
        private Color color;

        CategoryBuilder() {
        }

        @JsonProperty
        public CategoryBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public CategoryBuilder isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        @JsonProperty
        public CategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty
        @JsonDeserialize(contentUsing = ColorJsonDeserializer.class)
        public CategoryBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public IoCategory build() {
            return new IoCategory(id, name, color);
        }

        @Override
        public String toString() {
            return "CategoryBuilder{" +
                    "id=" + id +
                    ", isDefault=" + isDefault +
                    ", name='" + name + '\'' +
                    ", color=" + color +
                    '}';
        }
    }
}
