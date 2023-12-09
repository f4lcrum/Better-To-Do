package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

import java.util.List;

public class EventCrudService implements CrudService<Event> {

    private final Repository<Event> eventRepository;
    private final Validator<Event> eventValidator;

    public EventCrudService(Repository<Event> eventRepository,
                            Validator<Event> eventValidator) {
        this.eventRepository = eventRepository;
        this.eventValidator = eventValidator;
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public ValidationResult create(Event newEntity) {
        var validationResult = eventValidator.validate(newEntity);
        if (eventRepository.existsByGuid(newEntity.getGuid().toString())) {
            throw new EntityAlreadyExistsException("Event with given id already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            eventRepository.create(newEntity);
        }
        return validationResult;
    }

    @Override
    public ValidationResult update(Event entity) {
        var validationResult = eventValidator.validate(entity);
        if (validationResult.isValid()) {
            eventRepository.update(entity);
        }
        return validationResult;
    }

    @Override
    public void deleteByGuid(String id) {
        eventRepository.deleteByGuid(id);
    }

    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
    }
}
