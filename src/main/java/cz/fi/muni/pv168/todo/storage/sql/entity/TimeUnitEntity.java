package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.util.Objects;

public record TimeUnitEntity(String id, String name, Long hourCount, Long minuteCount) {

    public TimeUnitEntity(String id, String name, Long hourCount, Long minuteCount) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.hourCount = Objects.requireNonNull(hourCount, "hour count must not be null");
        this.minuteCount = Objects.requireNonNull(minuteCount, "minute count must not be null");
    }
}
