package cz.fi.muni.pv168.todo.io.mapper;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.io.entity.IoTimeUnit;

public class TimeUnitIoMapper implements IoMapper<TimeUnit, IoTimeUnit> {

    @Override
    public IoTimeUnit mapToIo(TimeUnit entity) {
        return new IoTimeUnit(
                entity.getGuid(),
                entity.isDefault(),
                entity.getName(),
                entity.getHours(),
                entity.getMinutes()
        );
    }

    @Override
    public TimeUnit mapToBusiness(IoTimeUnit entity) {
        return new TimeUnit(
                entity.getId(),
                entity.getIsDefault(),
                entity.getName(),
                entity.getHours(),
                entity.getMinutes()
        );
    }
}
