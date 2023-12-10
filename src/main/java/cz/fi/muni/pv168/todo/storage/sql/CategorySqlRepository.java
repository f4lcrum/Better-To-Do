package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataAccessObject;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;
import cz.fi.muni.pv168.todo.storage.sql.entity.CategoryEntity;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategorySqlRepository implements Repository<Category> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final EntityMapper<CategoryEntity, Category> categoryEntityMapper;

    public CategorySqlRepository(
            DataAccessObject<CategoryEntity> categoryDao,
            EntityMapper<CategoryEntity, Category> categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryEntityMapper = categoryMapper;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao
                .findAll()
                .stream()
                .map(categoryEntityMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Category newEntity) {
        categoryDao.create(categoryEntityMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Category entity) {
        var existingCategory = categoryDao.findById(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getGuid()));
        var updatedCategory = categoryEntityMapper.mapExistingEntityToDatabase(entity, existingCategory.id());
        categoryDao.update(updatedCategory);
    }

    @Override
    public void deleteByGuid(UUID guid) {
        categoryDao.deleteById(guid);
    }

    @Override
    public void deleteAll() {
        categoryDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(UUID guid) {
        return categoryDao.existsByGuid(guid);
    }

    @Override
    public Optional<Category> findByGuid(UUID guid) {
        return categoryDao
                .findById(guid)
                .map(categoryEntityMapper::mapToBusiness);
    }
}
