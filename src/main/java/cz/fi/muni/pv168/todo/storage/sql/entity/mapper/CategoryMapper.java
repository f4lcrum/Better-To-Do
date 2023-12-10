package cz.fi.muni.pv168.todo.storage.sql.entity.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.CategoryEntity;

import java.awt.Color;
import java.util.UUID;

public class CategoryMapper implements EntityMapper<CategoryEntity, Category> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;

    public CategoryMapper(
            DataAccessObject<CategoryEntity> categoryDao,
            EntityMapper<CategoryEntity, Category> categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category mapToBusiness(CategoryEntity entity) {
        UUID id = UUID.fromString(entity.id());
        categoryDao
                .findById(id)
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " + entity.id()));

        var color = new Color(entity.r(), entity.g(), entity.b());
        return new Category(
                UUID.fromString(entity.id()),
                entity.name(),
                color
        );
    }


    @Override
    public CategoryEntity mapNewEntityToDatabase(Category entity) {
        categoryDao
                .findById(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " + entity.getGuid()));
        var color = entity.getColour();

        return new CategoryEntity(
                entity.getGuid().toString(),
                entity.getName(),
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );

    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category entity, String dbId) {
        var categoryEntity = categoryDao
                .findById(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getGuid()));
        var color = entity.getColour();

        return new CategoryEntity(
                categoryEntity.id(),
                entity.getName(),
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );
    }

}
