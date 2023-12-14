package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

import java.util.List;
import java.util.UUID;

public final class CrudServiceImplementation<T extends Entity> implements CrudService<T> {

    private final Repository<T> repository;
    private final Validator<T> validator;
    private final String type;

    public CrudServiceImplementation(Repository<T> repository, Validator<T> validator, String type) {
        this.repository = repository;
        this.validator = validator;
        this.type = type;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public ValidationResult create(T newEntity) {
        var validationResult = validator.validate(newEntity);
        if (newEntity.getGuid().toString() == null || newEntity.getGuid().toString().isBlank()) {
            throw new EntityNoUUIDException(String.format("%s does not have assigned UUID", type));
        } else if (repository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException(String.format("%s with given guid already exists: %s", type, newEntity.getGuid()));
        }
        if (validationResult.isValid()) {
            repository.create(newEntity);
        }
        return validationResult;
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
