package cz.fi.muni.pv168.bosv.better_todo.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pv168.bosv.better_todo.Entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.Entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.Entity.Template;
import cz.fi.muni.pv168.bosv.better_todo.Entity.User;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NonNull
public record DatabaseSnapshot(
        @JsonProperty("categories")
        Collection<Category> categories,
        @JsonProperty("events")
        Collection<Event> events,
        @JsonProperty("templates")
        Collection<Template> templates,
        @JsonProperty("users")
        Collection<User> users
) {
    public DatabaseSnapshot(
            @NonNull Collection<Category> categories,
            @NonNull Collection<Event> events,
            @NonNull Collection<Template> templates,
            @NonNull Collection<User> users
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
