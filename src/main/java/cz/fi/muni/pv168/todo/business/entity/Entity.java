package cz.fi.muni.pv168.todo.business.entity;

import java.util.UUID;

public interface Entity {

    UUID getGuid();

    boolean isDefault();
}
