package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

import java.util.List;
import java.util.UUID;

public abstract class CrudServiceImpl<T extends Entity> implements CrudService<T> {

    protected final Repository<T> repository;
    protected final Validator<T> validator;

    public CrudServiceImpl(Repository<T> repository, Validator<T> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public ValidationResult update(T entity) {
        var validationResult = validator.validate(entity);
        if (validationResult.isValid()) {
            repository.update(entity);
        }
        return validationResult;
    }

    @Override
    public void deleteByGuid(UUID guid) {
        repository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
