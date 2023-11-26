package cz.fi.muni.pv168.todo.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.Template;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record DatabaseSnapshot(

        @JsonProperty
        Collection<Category> categories,
        @JsonProperty
        Collection<Event> events,
        @JsonProperty
        Collection<Template> templates
) {

    public DatabaseSnapshot(
            Collection<Category> categories,
            Collection<Event> events,
            Collection<Template> templates
    ) {
        this.categories = List.copyOf(categories);
        this.events = List.copyOf(events);
        this.templates = List.copyOf(templates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseSnapshot that = (DatabaseSnapshot) o;
        return Objects.equals(categories, that.categories) &&
                Objects.equals(events, that.events) &&
                Objects.equals(templates, that.templates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories, events, templates);
    }
}
