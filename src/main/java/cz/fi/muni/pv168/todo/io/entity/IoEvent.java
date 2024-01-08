package cz.fi.muni.pv168.todo.io.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = IoEvent.EventBuilder.class)
public class IoEvent implements IoEntity {

    private final UUID id;
    private final boolean isDefault;
    private final String name;
    private final IoCategory category;
    private final LocalDate date;
    private final LocalTime startTime;
    private final IoTimeUnit timeUnit;
    private final int duration;
    private final String description;

    public IoEvent(UUID id, String name, IoCategory category, LocalDate date, LocalTime startTime, IoTimeUnit timeUnit, int duration, String description) {
        this.id = id;
        this.isDefault = false;
        this.name = name;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.timeUnit = timeUnit;
        this.duration = duration;
        this.description = description;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean getIsDefault() {
        return this.isDefault;
    }

    public String getName() {
        return this.name;
    }

    public IoCategory getCategory() {
        return this.category;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public String getDescription() {
        return this.description;
    }

    public IoTimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IoEvent event = (IoEvent) o;
        return Objects.equals(event.id, this.id);
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class EventBuilder {

        private UUID id;
        private boolean isDefault;
        private String name;
        private IoCategory category;
        private LocalDate date;
        private LocalTime startTime;
        private IoTimeUnit timeUnit;
        private int duration;
        private String description;

        EventBuilder() {
        }

        @JsonProperty
        public EventBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public EventBuilder isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        @JsonProperty
        public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty
        public EventBuilder category(IoCategory category) {
            this.category = category;
            return this;
        }

        @JsonProperty
        public EventBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        @JsonProperty
        public EventBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        @JsonProperty
        public EventBuilder timeUnit(IoTimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        @JsonProperty
        public EventBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        @JsonProperty
        public EventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public IoEvent build() {
            return new IoEvent(this.id, this.name, this.category, this.date, this.startTime, this.timeUnit, this.duration, this.description);
        }

        @Override
        public String toString() {
            return "Event.EventBuilder{" +
                    "id=" + id +
                    ", isDefault=" + isDefault +
                    ", name='" + name + '\'' +
                    ", category=" + category +
                    ", date=" + date +
                    ", startTime=" + startTime +
                    ", timeUnit=" + timeUnit +
                    ", duration=" + duration +
                    ", description='" + description + '\'' +
                    '}';
        }

    }
}
