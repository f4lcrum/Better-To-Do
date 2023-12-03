package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;

import java.util.List;

public class EventSqlRepository implements Repository<Event> {

    private final DataAccessObject<EventEntity> eventDao;
    private final EntityMapper<EventEntity, Event> eventMapper;

    public EmployeeSqlRepository(
            DataAccessObject<EventEntity> eventDao,
            EntityMapper<EventEntity, Event> eventMapper) {
        this.eventDao = eventDao;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<Event> findAll() {
        return eventDao
                .findAll()
                .stream()
                .map(employeeMapper::mapToBusiness)
                .toList();

    }

    @Override
    public void create(Event newEntity) {
        eventDao.create(eventMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Event entity) {
        var existingEvent = eventDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Event not found, guid: " + entity.getGuid()));
        var updatedEventEntity = eventMapper
                .mapExistingEntityToDatabase(entity, existingEvent.id());

        eventDao.update(updatedEventEntity);

    }

    @Override
    public void deleteAll() {
        eventDao.deleteAll();
    }
}
