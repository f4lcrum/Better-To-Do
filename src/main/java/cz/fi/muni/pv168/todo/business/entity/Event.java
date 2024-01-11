package cz.fi.muni.pv168.todo.business.entity;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Event implements Entity {

    private final UUID id;
    private final boolean isDefault;
    private final String name;
    private final Category category;
    private final LocalDate date;
    private final LocalTime startTime;
    private final TimeUnit timeUnit;
    private final int duration;
    private final String description;

    public Event(UUID id, String name, Category category, LocalDate date, LocalTime startTime, TimeUnit timeUnit, int duration, String description) {
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

    public long getEventDuration() {
        return (timeUnit.getHours() * duration) * 60 + timeUnit.getMinutes() * duration;
    }

    public LocalDateTime calculateStart() {
        return LocalDateTime.of(date, startTime);
    }

    @Override
    public UUID getGuid() {
        return this.id;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    public Color getColour() {
        if (category.isDefault()) {
            return null;
        }
        return this.category.getColor();
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
        var hours = timeUnit.getHours() * duration;
        var minutes = timeUnit.getMinutes() * duration;
        return this.startTime.plusHours(hours).plusMinutes(minutes);
    }

    public String getDescription() {
        return this.description;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getDuration() {
        return duration;
    }

    public String getDurationString() {
        return String.format("%d %s", getDuration(), getTimeUnit().getName());
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
}
