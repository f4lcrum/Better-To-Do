package cz.fi.muni.pv168.todo.storage.sql.entity.mapper;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.storage.sql.entity.TimeUnitEntity;

import java.util.UUID;

/**
 * Mapper from the {@link TimeUnitEntity} to {@link TimeUnit}.
 *
 * @author Lukáš Bátora
 */
public class TimeUnitMapper implements EntityMapper<TimeUnitEntity, TimeUnit> {

    @Override
    public TimeUnit mapToBusiness(TimeUnitEntity entity) {
        return new TimeUnit(
                UUID.fromString(entity.id()),
                entity.name(),
                entity.hours(),
                entity.minutes()
        );
    }

    @Override
    public TimeUnitEntity mapNewEntityToDatabase(TimeUnit entity) {
        return new TimeUnitEntity(
                entity.getGuid().toString(),
                entity.getName(),
                entity.getHours(),
                entity.getMinutes()
        );
    }

    @Override
    public TimeUnitEntity mapExistingEntityToDatabase(TimeUnit entity, String dbId) {
        return new TimeUnitEntity(
                dbId,
                entity.getName(),
                entity.getHours(),
                entity.getMinutes()
        );
    }
}
