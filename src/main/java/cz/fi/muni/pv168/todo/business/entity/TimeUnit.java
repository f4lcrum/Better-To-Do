package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class TimeUnit implements Entity, Comparable<TimeUnit> {

    @JsonProperty
    private final UUID id;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final long hourCount;

    @JsonProperty
    private final long minuteCount;

    public TimeUnit(UUID id, String name, long hourCount, long minuteCount) {
        this.id = id;
        this.name = name;
        this.hourCount = hourCount;
        this.minuteCount = minuteCount;
    }

    @Override
    public int compareTo(TimeUnit other) {
        long totalMinutes = this.hourCount * 60 + this.minuteCount;
        long otherTotalMinutes = other.hourCount * 60 + other.minuteCount;

        return Long.compare(totalMinutes, otherTotalMinutes);
    }

    @Override
    public UUID getGuid() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getHourCount() {
        return hourCount;
    }

    public long getMinuteCount() {
        return minuteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeUnit timeUnit = (TimeUnit) o;
        return timeUnit.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
