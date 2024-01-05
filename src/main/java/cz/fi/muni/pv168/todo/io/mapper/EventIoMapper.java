package cz.fi.muni.pv168.todo.io.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.io.entity.IoCategory;
import cz.fi.muni.pv168.todo.io.entity.IoEvent;
import cz.fi.muni.pv168.todo.io.entity.IoTimeUnit;

public class EventIoMapper implements IoMapper<Event, IoEvent> {

    @Override
    public IoEvent mapToIo(Event entity) {
        return new IoEvent(
                entity.getGuid(),
                entity.getName(),
                new IoCategory(
                        entity.getCategory().getGuid(),
                        entity.getCategory().getName(),
                        entity.getColour()),
                entity.getDate(),
                entity.getStartTime(),
                new IoTimeUnit(
                        entity.getTimeUnit().getGuid(),
                        entity.getTimeUnit().isDefault(),
                        entity.getTimeUnit().getName(),
                        entity.getTimeUnit().getHours(),
                        entity.getTimeUnit().getMinutes()
                ),
                entity.getDuration(),
                entity.getDescription()
        );
    }

    @Override
    public Event mapToBusiness(IoEvent entity) {
        return new Event(
                entity.getId(),
                entity.getName(),
                new Category(
                        entity.getCategory().getId(),
                        entity.getCategory().getName(),
                        entity.getCategory().getColor()),
                entity.getDate(),
                entity.getStartTime(),
                new TimeUnit(
                        entity.getTimeUnit().getId(),
                        entity.getTimeUnit().getIsDefault(),
                        entity.getTimeUnit().getName(),
                        entity.getTimeUnit().getHours(),
                        entity.getTimeUnit().getMinutes()
                ),
                entity.getDuration(),
                entity.getDescription()
        );
    }
}
