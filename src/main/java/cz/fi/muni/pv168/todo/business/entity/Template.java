package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.awt.Color;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@JsonDeserialize(builder = Template.TemplateBuilder.class)
public class Template implements Entity {

    @JsonProperty
    private final UUID id;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String description;

    @JsonProperty
    private final Category category;

    @JsonProperty
    private final LocalTime startTime;

    private final TimeUnit timeUnit;

    @JsonProperty
    private final int timeUnitCount;

    public Template(UUID id, String name, String description, Category category,
                    LocalTime startTime, TimeUnit timeUnit, int timeUnitCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.startTime = startTime;
        this.timeUnit = timeUnit;
        this.timeUnitCount = timeUnitCount;
    }

    public static TemplateBuilder builder() {
        return new TemplateBuilder();
    }

    public long getTemplateDuration() {
        Duration duration = Duration.between(startTime, getEndTime());
        return duration.toMinutes();
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

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        var hours = timeUnit.getHourCount() * timeUnitCount;
        var minutes = timeUnit.getMinuteCount() * timeUnitCount;
        return this.startTime.plusHours(hours).plusMinutes(minutes);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public int getTimeUnitCount() {
        return timeUnitCount;
    }

    @com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class TemplateBuilder {

        private UUID id;
        private String name;
        private String description;
        private Category category;
        private LocalTime startTime;
        private TimeUnit timeUnit;
        private int timeUnitCount;

        TemplateBuilder() {
        }

        @JsonProperty
        public TemplateBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public TemplateBuilder name(String name) {
            this.name = name;
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
        public TemplateBuilder timeUnitCount(int timeUnitCount) {
            this.timeUnitCount = timeUnitCount;
            return this;
        }

        public Template build() {
            return new Template(this.id, this.name, this.description, this.category, this.startTime, this.timeUnit, this.timeUnitCount);
        }

        public String toString() {
            return "Template.TemplateBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", category=" + this.category + ", startTime=" + this.startTime + ", endTime=" + ", timeUnit=" + this.timeUnit  + ", timeUnitCount=" + this.timeUnitCount + ")";
        }
    }
}
