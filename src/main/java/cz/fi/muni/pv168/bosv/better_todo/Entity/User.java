package cz.fi.muni.pv168.bosv.better_todo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Jacksonized
@Builder
public class User implements Entity {
    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("login")
    private final String login;

    @JsonProperty("password")
    @Setter
    private String password;

    @Override
    public UUID getGuid() {
        return this.id;
    }
}
