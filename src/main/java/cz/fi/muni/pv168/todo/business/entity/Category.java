package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = Category.CategoryBuilder.class)
public class Category implements Entity {

    @JsonProperty
    private final UUID id;
    @JsonProperty
    private final boolean isDefault;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final Color color;

    public Category(UUID id, String name, Color color) {
        this.id = id;
        this.isDefault = false;
        this.name = name;
        this.color = color;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
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

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class CategoryBuilder {
        private UUID id;
        private boolean isDefault;
        private String name;
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
        public CategoryBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public Category build() {
            return new Category(this.id, this.name, this.color);
        }

        public String toString() {
            return "Category.CategoryBuilder(id=" + this.id + ", isDefault=" + this.isDefault + ", name=" + this.name + ", colour=" + this.color + ")";
        }
    }
}
