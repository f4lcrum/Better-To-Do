package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

import java.util.List;

public class CategoryCrudService extends CrudServiceImpl<Category> {

    public CategoryCrudService(Repository<Category> repository, Validator<Category> validator) {
        super(repository, validator);
    }

    @Override
    public ValidationResult create(Category newEntity) {
        var validationResult = validator.validate(newEntity);
        if (newEntity.getGuid().toString() == null || newEntity.getGuid().toString().isBlank()) {
            throw new EntityNoUUIDException("Category does not have assigned UUID");
        } else if (repository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Category with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            repository.create(newEntity);
        }
        return validationResult;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll().stream().filter(category -> !category.isDefault()).toList();
    }

    public List<Category> findAllWithDefault() {
        return repository.findAll();
    }

    public Category findDefault() {
        return repository.findAll().stream().filter(Category::isDefault).toList().get(0);
    }
}
