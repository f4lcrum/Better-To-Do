package cz.fi.muni.pv168.todo.storage.sql.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;

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
        Collection<TimeUnit> timeUnits
) {

    public DatabaseSnapshot(
            Collection<Category> categories,
            Collection<Event> events,
            Collection<Template> templates,
            Collection<TimeUnit> timeUnits
    ) {
        this.categories = List.copyOf(categories);
        this.events = List.copyOf(events);
        this.templates = List.copyOf(templates);
        this.timeUnits = List.copyOf(timeUnits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatabaseSnapshot that = (DatabaseSnapshot) o;
        return Objects.equals(categories, that.categories) &&
                Objects.equals(events, that.events) &&
                Objects.equals(templates, that.templates) &&
                Objects.equals(timeUnits, that.timeUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories, events, templates, timeUnits);
    }
}
