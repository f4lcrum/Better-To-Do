package cz.muni.fi.pv168.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event extends Identifiable {
    private String name;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private UUID userId;
}
