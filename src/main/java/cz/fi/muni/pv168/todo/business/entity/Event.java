package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.awt.Color;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@JsonDeserialize(builder = Event.EventBuilder.class)
public class Event implements Entity {

    @JsonProperty
    private final UUID id;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final Status status;

    @JsonProperty
    private final Category category;

    @JsonProperty
    private final LocalDate date;

    @JsonProperty
    private final LocalTime startTime;

    @JsonProperty
    private final LocalTime endTime;

    @JsonProperty
    private final String description;

    public Event(UUID id, String name, Status status, Category category, LocalDate date, LocalTime startTime, LocalTime endTime, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public long getEventDuration() {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes();
    }

    public LocalDateTime calculateStart() {
        return LocalDateTime.of(date, startTime);
    }

    public Status calculateStatus() {
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
        return this.status;
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
        return this.endTime;
    }

    public String getDescription() {
        return this.description;
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class EventBuilder {

        private UUID id;
        private UUID userId;
        private String name;
        private Status status;
        private Category category;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
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
        public EventBuilder status(Status status) {
            this.status = status;
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
        public EventBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        @JsonProperty
        public EventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Event build() {
            return new Event(this.id, this.name, this.status, this.category, this.date, this.startTime, this.endTime, this.description);
        }

        public String toString() {
            return "Event.EventBuilder(id=" + this.id + ", userId=" + this.userId + ", name=" + this.name + ", status=" + this.status + ", category=" + this.category + ", date=" + this.date + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", description=" + this.description + ")";
        }
    }
}
