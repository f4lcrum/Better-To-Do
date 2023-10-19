package cz.fi.muni.pv168.bosv.better_todo.Entity;

import java.util.Objects;

public class Category extends Identifiable {
    private String name;
    private CategoryColour colour;

    public Category(String name, CategoryColour colour) {
        setName(name);
        setColour(colour);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public CategoryColour getColour() {
        return colour;
    }

    public void setColour(CategoryColour colour) {
        this.colour = Objects.requireNonNull(colour, "colour must not be null");
    }

    public String getGuid() {
        return name + " " + colour;
    }
}
