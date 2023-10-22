package cz.fi.muni.pv168.bosv.better_todo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NonNull
@Jacksonized
@Builder
public class Event implements Entity {
    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("userId")
    private final UUID userId;

    @JsonProperty("name")
    private final String name;
    @JsonProperty("status")
    private final Status status;

    @JsonProperty("category")
    private final Category category;

    @JsonProperty("date")
    private final LocalDate date;
    @JsonProperty("startTime")
    private final LocalTime startTime;
    @JsonProperty("endTime")
    private final LocalTime endTime;

    @JsonProperty("description")
    private final String description;

    @Override
    public UUID getGuid() {
        return this.id;
    }
}
