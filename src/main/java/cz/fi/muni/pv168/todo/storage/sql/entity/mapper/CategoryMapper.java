package cz.fi.muni.pv168.todo.storage.sql.entity.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.storage.sql.entity.CategoryEntity;

import java.awt.Color;
import java.util.UUID;

public class CategoryMapper implements EntityMapper<CategoryEntity, Category> {


    public CategoryMapper() {}

    @Override
    public Category mapToBusiness(CategoryEntity entity) {
        var color = new Color(entity.color());
        return new Category(
                UUID.fromString(entity.id()),
                entity.name(),
                color
        );
    }


    @Override
    public CategoryEntity mapNewEntityToDatabase(Category entity) {
        var color = entity.getColor();

        return new CategoryEntity(
                entity.getGuid().toString(),
                entity.getName(),
                color.getRGB()
        );

    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category entity, String dbId) {
        var color = entity.getColor();

        return new CategoryEntity(
                dbId,
                entity.getName(),
                color.getRGB()
        );
    }

}
