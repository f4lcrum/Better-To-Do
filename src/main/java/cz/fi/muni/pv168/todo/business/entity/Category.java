package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.awt.Color;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = Category.CategoryBuilder.class)
public class Category implements Entity{

    @JsonProperty
    private final UUID id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Color colour;

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

    public void setName(String name) { this.name = name;}

    public Color getColour() {
        return this.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setColour(Color colour) { this.colour = colour; }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class CategoryBuilder {
        private UUID id;
        private String name;
        private Color colour;

        CategoryBuilder() {
        }

        @JsonProperty
        public CategoryBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public CategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty
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
