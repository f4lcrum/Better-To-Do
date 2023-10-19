package cz.muni.fi.pv168.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event extends Identifiable {
    private String name;
    private LocalDate date;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private int userId;
    private boolean isTemplate;
}
