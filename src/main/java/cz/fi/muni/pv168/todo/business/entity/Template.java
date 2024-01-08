package cz.fi.muni.pv168.todo.business.entity;

import java.awt.Color;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Template implements Entity {

    private final UUID id;
    private final boolean isDefault;
    private final String name;
    private final String eventName;
    private final Category category;
    private final LocalTime startTime;
    private final TimeUnit timeUnit;
    private final int duration;
    private final String description;

    public Template(UUID id, String name, String eventName, Category category,
                    LocalTime startTime, TimeUnit timeUnit, int duration, String description) {
        this.id = id;
        this.isDefault = false;
        this.name = name;
        this.eventName = eventName;
        this.description = description;
        this.category = category;
        this.startTime = startTime;
        this.timeUnit = timeUnit;
        this.duration = duration;
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
        return this.category.getColor();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public String getEventName() {
        return this.eventName;
    }

    public LocalTime getEndTime() {
        var hours = timeUnit.getHours() * duration;
        var minutes = timeUnit.getMinutes() * duration;
        return this.startTime.plusHours(hours).plusMinutes(minutes);
    }

    public TimeUnit getTimeUnit() {
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
        Template template = (Template) o;
        return Objects.equals(template.id, this.id);
    }
}
