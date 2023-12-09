package cz.fi.muni.pv168.todo.storage.sql.entity.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.CategoryEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.TemplateEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.TimeUnitEntity;
import java.awt.Color;
import java.util.UUID;

public class TemplateMapper implements EntityMapper<TemplateEntity, Template> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;
    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper;


    public TemplateMapper(
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
    public Template mapToBusiness(TemplateEntity entity) {
        var category = categoryDao
                .findById(entity.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.categoryId()));
        var timeUnit = timeUnitDao
                .findById(entity.timeUnitId())
                .map(timeUnitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("TimeUnit not found, id: " +
                        entity.timeUnitId()));
        return new Template(
                UUID.fromString(entity.id()),
                entity.name(),
                entity.description(),
                category,
                entity.startTime(),
                timeUnit,
                entity.timeUnitCount()
        );
    }

    @Override
    public TemplateEntity mapNewEntityToDatabase(Template entity) {
        var categoryEntity = categoryDao
                .findById(entity.getCategory().getGuid().toString())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));
        var timeUnitEntity = timeUnitDao
                .findById(entity.getTimeUnit().getGuid().toString())
                .orElseThrow(() -> new DataStorageException("TimeUnit not found, guid: " +
                        entity.getTimeUnit().getGuid()));
        return new TemplateEntity(
                entity.getGuid().toString(),
                entity.getName(),
                categoryEntity.id(),
                entity.getStartTime(),
                timeUnitEntity.id(),
                entity.getTimeUnitCount(),
                entity.getDescription()
        );
    }

    @Override
    public TemplateEntity mapExistingEntityToDatabase(Template entity, String dbId) {
        var categoryEntity = categoryDao
                .findById(entity.getCategory().getGuid().toString())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));
        var timeUnitEntity = timeUnitDao
                .findById(entity.getTimeUnit().getGuid().toString())
                .orElseThrow(() -> new DataStorageException("TimeUnit not found, guid: " +
                        entity.getTimeUnit().getGuid()));
        return new TemplateEntity(
                dbId,
                entity.getName(),
                categoryEntity.id(),
                entity.getStartTime(),
                timeUnitEntity.id(),
                entity.getTimeUnitCount(),
                entity.getDescription()
        );
    }
}
