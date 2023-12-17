package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

public class TimeUnitCrudService extends CrudServiceImplementation<TimeUnit> {

    public TimeUnitCrudService(Repository<TimeUnit> repository, Validator<TimeUnit> validator) {
        super(repository, validator);
    }

    @Override
    public ValidationResult create(TimeUnit newEntity) {
        var validationResult = validator.validate(newEntity);
        if (newEntity.getGuid().toString() == null || newEntity.getGuid().toString().isBlank()) {
            throw new EntityNoUUIDException("Event does not have assigned UUID");
        } else if (repository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Event with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            repository.create(newEntity);
        }
        return validationResult;
    }
}
