package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.util.Objects;

public record TimeUnitEntity(String id, boolean isDefault, String name, Long hours, Long minutes) {

    public TimeUnitEntity(String id, boolean isDefault, String name, Long hours, Long minutes) {
        this.id = id;
        this.isDefault = isDefault;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.hours = Objects.requireNonNull(hours, "hours must not be null");
        this.minutes = Objects.requireNonNull(minutes, "minutes must not be null");
    }
}
