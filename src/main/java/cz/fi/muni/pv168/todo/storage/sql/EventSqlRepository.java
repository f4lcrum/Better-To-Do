package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.EventEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class EventSqlRepository implements Repository<Event> {

    private final DataAccessObject<EventEntity> eventDao;
    private final EntityMapper<EventEntity, Event> eventMapper;

    public EventSqlRepository(
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
                .map(eventMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Event newEntity) {
        eventDao.create(eventMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Event entity) {
        var existingEvent = eventDao.findById(entity.getGuid().toString())
                .orElseThrow(() -> new DataStorageException("Event not found, id: " + entity.getGuid()));
        var updatedEventEntity = eventMapper
                .mapExistingEntityToDatabase(entity, existingEvent.id());
        eventDao.update(updatedEventEntity);
    }

    @Override
    public void deleteByGuid(String id) {
        eventDao.deleteById(id);
    }

    @Override
    public Optional<Event> findByGuid(String id) {
        return eventDao
                .findById(id)
                .map(eventMapper::mapToBusiness);
    }

    @Override
    public void deleteAll() {
        eventDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String id) {
        return eventDao.existsByGuid(id);
    }
}
