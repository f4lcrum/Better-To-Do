package cz.fi.muni.pv168.bosv.better_todo.Entity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

public class Event extends Identifiable {
    private String name;
    private Status status;

    private Category category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private UUID userId;
    private boolean isTemplate;

    public Event(
            String name,
            Status status,
            Category category,
            LocalDateTime eventStart,
            LocalDateTime eventEnd,
            String description) {
        super();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = Objects.requireNonNull(category, "category must not be null");
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = Objects.requireNonNull(startTime, "startime must not be null");
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = Objects.requireNonNull(endTime, "endTime must not be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description, "description must not be null");
    }


     /* @Override */
    public String getGuid() {
        return name;
    }
}
