package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

public class EventCrudService extends CrudServiceImplementation<Event> {

    public EventCrudService(Repository<Event> repository, Validator<Event> validator) {
        super(repository, validator);
    }

    @Override
    public ValidationResult create(Event newEntity) {
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
