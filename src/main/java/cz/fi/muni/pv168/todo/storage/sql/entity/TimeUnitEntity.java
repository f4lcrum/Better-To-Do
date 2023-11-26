package cz.fi.muni.pv168.todo.storage.sql.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Representation of Department entity in a SQL database.
 */
public record TimeUnitEntity(UUID id,
                             String name,
                             long hourCount,
                             long minuteCount) {
    public TimeUnitEntity(UUID id,
                          String name,
                          long hourCount,
                          long minuteCount) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "guid must not be null");
        this.hourCount = hourCount;
        this.minuteCount = minuteCount;
    }

    public TimeUnitEntity(String name,
                          long hourCount,
                          long minuteCount) {
        this(null, name, hourCount, minuteCount);
    }
}
