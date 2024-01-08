package cz.fi.muni.pv168.todo.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.fi.muni.pv168.todo.io.entity.IoCategory;
import cz.fi.muni.pv168.todo.io.entity.IoEvent;
import cz.fi.muni.pv168.todo.io.entity.IoTemplate;
import cz.fi.muni.pv168.todo.io.entity.IoTimeUnit;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record IoDatabaseSnapshot(

        @JsonProperty
        Collection<IoCategory> categories,
        @JsonProperty
        Collection<IoEvent> events,
        @JsonProperty
        Collection<IoTemplate> templates,
        @JsonProperty
        Collection<IoTimeUnit> timeUnits
) {

    public IoDatabaseSnapshot(
            Collection<IoCategory> categories,
            Collection<IoEvent> events,
            Collection<IoTemplate> templates,
            Collection<IoTimeUnit> timeUnits
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
        IoDatabaseSnapshot that = (IoDatabaseSnapshot) o;
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
