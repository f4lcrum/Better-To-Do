package cz.fi.muni.pv168.todo.io.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = IoTimeUnit.TimeUnitBuilder.class)
public class IoTimeUnit implements IoEntity {

    private final UUID id;
    private final boolean isDefault;
    private final String name;
    private final long hours;
    private final long minutes;

    public IoTimeUnit(UUID id, boolean isDefault, String name, long hours, long minutes) {
        this.id = id;
        this.isDefault = isDefault;
        this.name = name;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean getIsDefault() {
        return isDefault;
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
        IoTimeUnit timeUnit = (IoTimeUnit) o;
        return Objects.equals(id, timeUnit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class TimeUnitBuilder {

        private UUID id;
        private boolean isDefault;
        private String name;
        private long hours;
        private long minutes;

        TimeUnitBuilder() {
        }

        public TimeUnitBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public TimeUnitBuilder isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public TimeUnitBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TimeUnitBuilder hours(long hours) {
            this.hours = hours;
            return this;
        }

        public TimeUnitBuilder minutes(long minutes) {
            this.minutes = minutes;
            return this;
        }

        public IoTimeUnit build() {
            return new IoTimeUnit(id, isDefault, name, hours, minutes);
        }

        @Override
        public String toString() {
            return "TimeUnitBuilder{" +
                    "id=" + id +
                    ", isDefault=" + isDefault +
                    ", name='" + name + '\'' +
                    ", hours=" + hours +
                    ", minutes=" + minutes +
                    '}';
        }
    }
}
