package cz.fi.muni.pv168.bosv.better_todo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NonNull
public class Template implements Entity {
    private final UUID id;
    private final UUID userId;

    private final String name;
    private final String description;

    private final Category category;

    private final LocalTime startTime;
    private final LocalTime endTime;

    @Override
    public UUID getGuid() {
        return this.id;
    }
}
