package cz.fi.muni.pv168.todo.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.Template;
import cz.fi.muni.pv168.todo.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record DatabaseSnapshot(
        @JsonProperty
        Collection<Category> categories,
        @JsonProperty
        Collection<Event> events,
        @JsonProperty
        Collection<Template> templates,
        @JsonProperty
        Collection<User> users
) {
    public DatabaseSnapshot(
            Collection<Category> categories,
            Collection<Event> events,
            Collection<Template> templates,
            Collection<User> users
    ) {
        this.categories = List.copyOf(categories);
        this.events = List.copyOf(events);
        this.templates = List.copyOf(templates);
        this.users = List.copyOf(users);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseSnapshot that = (DatabaseSnapshot) o;
        return Objects.equals(categories, that.categories) &&
                Objects.equals(events, that.events) &&
                Objects.equals(templates, that.templates) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories, events, templates, users);
    }
}
