package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class TimeUnit implements Entity, Comparable<TimeUnit> {

    @JsonProperty
    private final UUID id;

    @JsonProperty
    private final boolean isDefault;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final long hours;

    @JsonProperty
    private final long minutes;

    public TimeUnit(UUID id, boolean isDefault, String name, long hours, long minutes) {
        this.id = id;
        this.isDefault = isDefault;
        this.name = name;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public int compareTo(TimeUnit other) {
        long totalMinutes = this.hours * 60 + this.minutes;
        long otherTotalMinutes = other.hours * 60 + other.minutes;

        return Long.compare(totalMinutes, otherTotalMinutes);
    }

    @Override
    public UUID getGuid() {
        return id;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    public String getName() {
        return name;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeUnit timeUnit = (TimeUnit) o;
        return Objects.equals(timeUnit.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
