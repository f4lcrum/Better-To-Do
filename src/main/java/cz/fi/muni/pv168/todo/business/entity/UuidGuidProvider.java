package cz.fi.muni.pv168.todo.business.entity;

import java.util.UUID;

public class UuidGuidProvider implements GuidProvider {

    @Override
    public String newGuid() {
        return UUID.randomUUID().toString();
    }
}
