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
        var category = categoryDao
                .findById(entity.id())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " + entity.id()));

        return new Category(
                UUID.fromString(entity.id()),
                entity.name(),
                new Color(entity.color())
        );
    }


    @Override
    public CategoryEntity mapNewEntityToDatabase(Category entity) {
        var categoryEntity = categoryDao
                .findById(String.valueOf(entity.getId()))
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " + entity.getId()));

        return new CategoryEntity(
                entity.getId().toString(),
                entity.getName(),
                entity.getColour().getRGB()
        );

    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category entity, String dbId) {
        var categoryEntity = categoryDao
                .findById(String.valueOf(entity.getId()))
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getId()));

        return new CategoryEntity(
                categoryEntity.id(),
                entity.getName(),
                entity.getColour().getRGB()
        );
    }

}
