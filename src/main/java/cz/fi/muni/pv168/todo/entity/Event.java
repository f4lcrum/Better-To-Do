package cz.fi.muni.pv168.todo.entity;

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
    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("userId")
    private final UUID userId;

    @JsonProperty("name")
    private final String name;
    @JsonProperty("status")
    private final Status status;

    @JsonProperty("category")
    private final Category category;

    @JsonProperty("date")
    private final LocalDate date;
    @JsonProperty("startTime")
    private final LocalTime startTime;
    @JsonProperty("endTime")
    private final LocalTime endTime;

    @JsonProperty("description")
    private final String description;

    public Event(UUID id, UUID userId, String name, Status status, Category category, LocalDate date, LocalTime startTime, LocalTime endTime, String description) {
        this.id = id;
        this.userId = userId;
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

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
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

        @JsonProperty("id")
        public EventBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty("userId")
        public EventBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        @JsonProperty("name")
        public EventBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty("status")
        public EventBuilder status(Status status) {
            this.status = status;
            return this;
        }

        @JsonProperty("category")
        public EventBuilder category(Category category) {
            this.category = category;
            return this;
        }

        @JsonProperty("date")
        public EventBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        @JsonProperty("startTime")
        public EventBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        @JsonProperty("endTime")
        public EventBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        @JsonProperty("description")
        public EventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Event build() {
            return new Event(this.id, this.userId, this.name, this.status, this.category, this.date, this.startTime, this.endTime, this.description);
        }

        public String toString() {
            return "Event.EventBuilder(id=" + this.id + ", userId=" + this.userId + ", name=" + this.name + ", status=" + this.status + ", category=" + this.category + ", date=" + this.date + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", description=" + this.description + ")";
        }
    }
}
