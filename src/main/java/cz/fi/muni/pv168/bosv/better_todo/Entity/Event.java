package cz.fi.muni.pv168.bosv.better_todo.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event extends Identifiable {
    private String name;
    private Status status;

    private Category category;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private UUID userId;
}
