package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.repository.EventRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.EventEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventSqlRepository implements EventRepository {

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
        var existingEvent = eventDao.findById(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Event not found, id: " + entity.getGuid()));
        var updatedEventEntity = eventMapper
                .mapExistingEntityToDatabase(entity, existingEvent.id());
        eventDao.update(updatedEventEntity);
    }

    @Override
    public void deleteByGuid(UUID id) {
        eventDao.deleteById(id);
    }

    @Override
    public Optional<Event> findByGuid(UUID id) {
        return eventDao
                .findById(id)
                .map(eventMapper::mapToBusiness);
    }

    @Override
    public void deleteAll() {
        eventDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(UUID id) {
        return eventDao.existsByGuid(id);
    }
}
