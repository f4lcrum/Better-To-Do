package cz.fi.muni.pv168.todo.io.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.io.entity.IoCategory;

public final class CategoryIoMapper implements IoMapper<Category, IoCategory> {


    @Override
    public IoCategory mapToIo(Category entity) {
        return new IoCategory(
                entity.getGuid(),
                entity.getName(),
                entity.getColor()
        );
    }

    @Override
    public Category mapToBusiness(IoCategory entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getColor()
        );
    }
}
