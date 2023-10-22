package cz.fi.muni.pv168.bosv.better_todo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@AllArgsConstructor
@Getter
@NonNull
@Jacksonized
@Builder
public class Category implements Entity {
    @JsonProperty("id")
    private final UUID id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("colour")
    private final CategoryColour colour;

    @Override
    public UUID getGuid() {
        return this.id;
    }
}
