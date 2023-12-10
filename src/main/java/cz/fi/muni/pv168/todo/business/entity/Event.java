package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = Event.EventBuilder.class)
public class Event implements Entity {

    @JsonProperty
    private final UUID id;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final Category category;

    @JsonProperty
    private final LocalDate date;

    @JsonProperty
    private final LocalTime startTime;

    @JsonProperty
    private final TimeUnit timeUnit;

    @JsonProperty
    private final int timeUnitCount;

    @JsonProperty
    private final String description;

    public Event(UUID id, String name, Category category, LocalDate date, LocalTime startTime, TimeUnit timeUnit, int timeUnitCount, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.timeUnit = timeUnit;
        this.timeUnitCount = timeUnitCount;
        this.description = description;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public long getEventDuration() {
        return (timeUnit.getHourCount() * timeUnitCount) * 60 + timeUnit.getMinuteCount() * timeUnitCount;
    }

    public LocalDateTime calculateStart() {
        return LocalDateTime.of(date, startTime);
    }

    @Override
    public UUID getGuid() {
        return this.id;
    }

    public Color getColour() {
        return this.category.getColour();
    }

    public String getName() {
        return this.name;
    }

    public Status getStatus() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = calculateStart();
        if (now.isBefore(startDateTime)) {
            return Status.PLANNED;
        }
        if (now.isBefore(startDateTime.plusMinutes(getEventDuration()))) {
            return Status.IN_PROGRESS;
        }
        return Status.DONE;
    }

    public Category getCategory() {
        return this.category;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        var hours = timeUnit.getHourCount() * timeUnitCount;
        var minutes = timeUnit.getMinuteCount() * timeUnitCount;
        return this.startTime.plusHours(hours).plusMinutes(minutes);
    }

    public String getDescription() {
        return this.description;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getTimeUnitCount() {
        return timeUnitCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(event.id, this.id);
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class EventBuilder {

        private UUID id;
        private UUID userId;
        private String name;
        private Category category;
        private LocalDate date;
        private LocalTime startTime;
        private TimeUnit timeUnit;
        private int timeUnitCount;
        private String description;

        EventBuilder() {
        }

        @JsonProperty
        public EventBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public EventBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        @JsonProperty
        public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty
        public EventBuilder category(Category category) {
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
        public EventBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        @JsonProperty
        public EventBuilder timeUnitCount(int timeUnitCount) {
            this.timeUnitCount = timeUnitCount;
            return this;
        }

        @JsonProperty
        public EventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Event build() {
            return new Event(this.id, this.name, this.category, this.date, this.startTime, this.timeUnit, this.timeUnitCount, this.description);
        }

        public String toString() {
            return "Event.EventBuilder(id=" + this.id + ", userId=" + this.userId + ", name=" + this.name + ", category=" + this.category + ", date=" + this.date + ", startTime=" + this.startTime + ", timeUnit=" + this.timeUnit + ", timeUnitCount=" + this.timeUnitCount + ", description=" + this.description + ")";
        }
    }
}
