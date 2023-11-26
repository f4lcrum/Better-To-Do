package cz.fi.muni.pv168.todo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.awt.Color;
import java.time.Duration;
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

    @JsonProperty
    private final LocalTime endTime;

    public Template(UUID id, String name, String description, Category category, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TemplateBuilder builder() {
        return new TemplateBuilder();
    }

    public long getTemplateDuration() {
        Duration duration = Duration.between(startTime, endTime);
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
        return this.endTime;
    }

    @com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class TemplateBuilder {

        private UUID id;
        private UUID userId;
        private String name;
        private String description;
        private Category category;
        private LocalTime startTime;
        private LocalTime endTime;

        TemplateBuilder() {
        }

        @JsonProperty
        public TemplateBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public TemplateBuilder userId(UUID userId) {
            this.userId = userId;
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
        public TemplateBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Template build() {
            return new Template(this.id, this.name, this.description, this.category, this.startTime, this.endTime);
        }

        public String toString() {
            return "Template.TemplateBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", category=" + this.category + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ")";
        }
    }
}
