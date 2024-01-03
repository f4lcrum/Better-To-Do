package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.awt.Color;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@JsonDeserialize(builder = Template.TemplateBuilder.class)
public class Template implements Entity {

    @JsonProperty
    private final UUID id;
    @JsonProperty
    private final boolean isDefault;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final String eventName;
    @JsonProperty
    private final Category category;
    @JsonProperty
    private final LocalTime startTime;
    private final TimeUnit timeUnit;
    @JsonProperty
    private final int duration;
    @JsonProperty
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

    public static TemplateBuilder builder() {
        return new TemplateBuilder();
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

    @com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class TemplateBuilder {

        private UUID id;
        private boolean isDefault;
        private String name;
        private String eventName;
        private String description;
        private Category category;
        private LocalTime startTime;
        private TimeUnit timeUnit;
        private int duration;

        TemplateBuilder() {
        }

        @JsonProperty
        public TemplateBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public TemplateBuilder isDefault(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        @JsonProperty
        public TemplateBuilder name(String name) {
            this.name = name;
            return this;
        }

        @JsonProperty
        public TemplateBuilder eventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        @JsonProperty
        public TemplateBuilder description(String description) {
            this.description = description;
            return this;
        }

        @JsonProperty
        public TemplateBuilder category(Category category) {
            this.category = category;
            return this;
        }

        @JsonProperty
        public TemplateBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        @JsonProperty
        public TemplateBuilder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        @JsonProperty
        public TemplateBuilder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Template build() {
            return new Template(this.id, this.name, this.eventName, this.category, this.startTime, this.timeUnit, this.duration, this.description);
        }

        public String toString() {
            return "Template.TemplateBuilder(id=" + this.id + ", isDefault=" + this.isDefault + ", name=" + this.name + ", eventName=" + this.eventName + ", description=" + this.description + ", category=" + this.category + ", startTime=" + this.startTime + ", endTime=" + ", timeUnit=" + this.timeUnit + ", duration=" + this.duration + ")";
        }
    }
}
