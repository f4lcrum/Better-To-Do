package cz.fi.muni.pv168.bosv.better_todo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User implements Entity {
    private final UUID id;

    private final String login;

    @Setter
    private String password;

    @Override
    public UUID getGuid() {
        return this.id;
    }
}
