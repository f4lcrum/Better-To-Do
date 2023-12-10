package cz.fi.muni.pv168.todo.storage.sql.entity.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.CategoryEntity;

import java.awt.Color;
import java.util.UUID;

public class CategoryMapper implements EntityMapper<CategoryEntity, Category> {


    public CategoryMapper() {};

    @Override
    public Category mapToBusiness(CategoryEntity entity) {
        var color = new Color(entity.r(), entity.g(), entity.b());
        return new Category(
                UUID.fromString(entity.id()),
                entity.name(),
                color
        );
    }


    @Override
    public CategoryEntity mapNewEntityToDatabase(Category entity) {
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
        var color = entity.getColour();

        return new CategoryEntity(
                dbId,
                entity.getName(),
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );
    }

}
