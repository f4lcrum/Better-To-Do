package cz.fi.muni.pv168.todo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.awt.Color;
import java.util.UUID;

@JsonDeserialize(builder = Category.CategoryBuilder.class)
public class Category implements Entity {
    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("colour")
    private final Color colour;

    public Category(UUID id, String name, Color colour) {
        this.id = id;
        this.name = name;
        this.colour = colour;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    @Override
    public UUID getGuid() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colour=" + colour +
                '}';
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Color getColour() {
        return this.colour;
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class CategoryBuilder {
        private UUID id;
        private String name;
        private Color colour;

        CategoryBuilder() {
        }

        @JsonProperty("id")
        public CategoryBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty("name")
        public CategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("colour")
        public CategoryBuilder colour(Color colour) {
            this.colour = colour;
            return this;
        }

        public Category build() {
            return new Category(this.id, this.name, this.colour);
        }

        public String toString() {
            return "Category.CategoryBuilder(id=" + this.id + ", name=" + this.name + ", colour=" + this.colour + ")";
        }
    }
}
