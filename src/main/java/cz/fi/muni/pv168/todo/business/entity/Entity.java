package cz.fi.muni.pv168.todo.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface Entity {
    @JsonIgnore
    UUID getGuid();
}
