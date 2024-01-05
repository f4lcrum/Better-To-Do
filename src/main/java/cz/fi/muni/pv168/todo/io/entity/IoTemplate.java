package cz.fi.muni.pv168.todo.io.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = IoTemplate.TemplateBuilder.class)
public class IoTemplate implements IoEntity {

    private final UUID id;
    private final boolean isDefault;
    private final String name;
    private final String eventName;
    private final IoCategory category;
    private final LocalTime startTime;
    private final IoTimeUnit timeUnit;
    private final int duration;
    private final String description;

    public IoTemplate(UUID id, String name, String eventName, IoCategory category, LocalTime startTime, IoTimeUnit timeUnit, int duration, String description) {
        this.id = id;
        this.isDefault = false;
        this.name = name;
        this.eventName = eventName;
        this.category = category;
        this.startTime = startTime;
        this.timeUnit = timeUnit;
        this.duration = duration;
        this.description = description;
    }

    public static TemplateBuilder builder() {
        return new TemplateBuilder();
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

    public String getEventName() {
        return eventName;
    }

    public IoCategory getCategory() {
        return category;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public IoTimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IoTemplate template = (IoTemplate) o;
        return Objects.equals(id, template.id);
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class TemplateBuilder {

        private UUID id;
        private boolean isDefault;
        private String name;
        private String eventName;
        private IoCategory category;
        private LocalTime startTime;
        private IoTimeUnit timeUnit;
        private int duration;
        private String description;

        TemplateBuilder() {
        }

        public TemplateBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public TemplateBuilder isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public TemplateBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TemplateBuilder eventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public TemplateBuilder category(IoCategory category) {
            this.category = category;
            return this;
        }

        public TemplateBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public TemplateBuilder timeUnit(IoTimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public TemplateBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public TemplateBuilder description(String description) {
            this.description = description;
            return this;
        }

        public IoTemplate build() {
            return new IoTemplate(id, name, eventName, category, startTime, timeUnit, duration, description);
        }

        @Override
        public String toString() {
            return "TemplateBuilder{" +
                    "id=" + id +
                    ", isDefault=" + isDefault +
                    ", name='" + name + '\'' +
                    ", eventName='" + eventName + '\'' +
                    ", category=" + category +
                    ", startTime=" + startTime +
                    ", timeUnit=" + timeUnit +
                    ", duration=" + duration +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
