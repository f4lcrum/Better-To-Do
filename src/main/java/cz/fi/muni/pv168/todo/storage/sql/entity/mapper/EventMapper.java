package cz.fi.muni.pv168.todo.storage.sql.entity.mapper;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventMapper implements EntityMapper<EventEntity, Event> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;

    public EmployeeMapper(
            DataAccessObject<CategoryEntity> categoryDao,
            EntityMapper<CategoryEntity, Category> categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Event mapToBusiness(EventEntity entity) {
        var category = categoryDao
                .findById(entity.getCategoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getCategorytId()));
        var duration = entity.getDatetime().getTime();
        return new Event(
                entity.getGuid(),
                entity.getName(),
                category,
                entity.getDatetime().getDate(),
                entity.getDatetime().getTime(),
                entity.getDatetime().getTime() + duration,
                entity.getDescription()
        );

    }

    @Override
    public EventEntity mapNewEntityToDatabase(Event entity) {
        var categoryEntity = categoryDao
                .findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));
        var duration = null;
        return new EmployeeEntity(
                entity.getGuid(),
                entity.getName(),
                categoryEntity.getGuid(),
                LocalDateTime.of(entity.getDate(), entity.getStartTime()),
                duration,
                entity.getDescription()
        );

    }

    @Override
    public EventEntity mapExistingEntityToDatabase(Event entity, Long dbId) {
        var categoryEntity = categoryDao
                .findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));

        return new EmployeeEntity(

        );

    }
}
