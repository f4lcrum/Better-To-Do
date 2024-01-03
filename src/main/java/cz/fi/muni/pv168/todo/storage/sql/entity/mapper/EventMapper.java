package cz.fi.muni.pv168.todo.storage.sql.entity.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.CategoryEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.EventEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.TimeUnitEntity;

import java.util.UUID;

public class EventMapper implements EntityMapper<EventEntity, Event> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;
    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper;

    public EventMapper(
            DataAccessObject<CategoryEntity> categoryDao,
            EntityMapper<CategoryEntity, Category> categoryMapper,
            DataAccessObject<TimeUnitEntity> timeUnitDao,
            EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.timeUnitDao = timeUnitDao;
        this.timeUnitMapper = timeUnitMapper;
    }

    @Override
    public Event mapToBusiness(EventEntity entity) {
        var categoryId = UUID.fromString(entity.categoryId());
        var category = categoryDao
                .findById(categoryId)
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.categoryId()));
        var timeUnitId = UUID.fromString(entity.timeUnitId());
        var timeUnit = timeUnitDao
                .findById(timeUnitId)
                .map(timeUnitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("TimeUnit not found, id: " +
                        entity.timeUnitId()));
        return new Event(
                UUID.fromString(entity.id()),
                entity.name(),
                category,
                entity.date(),
                entity.startTime(),
                timeUnit,
                entity.duration(),
                entity.description()
        );
    }

    @Override
    public EventEntity mapNewEntityToDatabase(Event entity) {
        var categoryEntity = categoryDao
                .findById(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));
        var timeUnitEntity = timeUnitDao
                .findById(entity.getTimeUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("TimeUnit not found, guid: " +
                        entity.getTimeUnit().getGuid()));
        return new EventEntity(
                entity.getGuid().toString(),
                entity.getName(),
                categoryEntity.id(),
                entity.getDate(),
                entity.getStartTime(),
                timeUnitEntity.id(),
                entity.getDuration(),
                entity.getDescription()
        );
    }

    @Override
    public EventEntity mapExistingEntityToDatabase(Event entity, String dbId) {
        var categoryEntity = categoryDao
                .findById(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));
        var timeUnitEntity = timeUnitDao
                .findById(entity.getTimeUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("TimeUnit not found, guid: " +
                        entity.getTimeUnit().getGuid()));
        return new EventEntity(
                dbId,
                entity.getName(),
                categoryEntity.id(),
                entity.getDate(),
                entity.getStartTime(),
                timeUnitEntity.id(),
                entity.getDuration(),
                entity.getDescription()
        );
    }
}
