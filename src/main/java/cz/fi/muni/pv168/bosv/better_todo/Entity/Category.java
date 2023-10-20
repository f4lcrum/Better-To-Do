package cz.fi.muni.pv168.bosv.better_todo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
@Getter
@NonNull
public class Category implements Entity {
    private final UUID id;
    private final String name;
    private final CategoryColour colour;

    @Override
    public UUID getGuid() {
        return this.id;
    }
}
