package cz.muni.fi.pv168.Entity;

import java.time.Duration;
import java.time.LocalDate;

public class Event extends Identifiable {
    private String name;
    private LocalDate date;
    private Status status;
    private Duration duration;
    private String description;
    private int userId;
    private boolean isTemplate;
}
