package cz.fi.muni.pv168.todo.io.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public interface IoEntity {
    @JsonProperty
    UUID getId();

    @JsonProperty
    boolean getIsDefault();
}
