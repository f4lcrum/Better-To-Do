package cz.fi.muni.pv168.bosv.better_todo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface Entity {
    @JsonIgnore
    UUID getGuid();
}
