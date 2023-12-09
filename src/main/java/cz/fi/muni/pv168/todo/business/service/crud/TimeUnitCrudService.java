package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

import java.util.List;


public final class TimeUnitCrudService implements CrudService<TimeUnit> {

    private final Repository<TimeUnit> timeUnitRepository;
    private final Validator<TimeUnit> timeUnitValidator;

    public TimeUnitCrudService(Repository<TimeUnit> timeUnitRepository,
                               Validator<TimeUnit> timeUnitValidator) {
        this.timeUnitRepository = timeUnitRepository;
        this.timeUnitValidator = timeUnitValidator;
    }

    @Override
    public List<TimeUnit> findAll() {
        return timeUnitRepository.findAll();
    }

    @Override
    public ValidationResult create(TimeUnit newEntity) {
        var validationResult = timeUnitValidator.validate(newEntity);
        if (timeUnitRepository.existsByGuid(newEntity.getGuid().toString())) {
            throw new EntityAlreadyExistsException("Time unit with given id already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            timeUnitRepository.create(newEntity);
        }
        return validationResult;
    }

    @Override
    public ValidationResult update(TimeUnit entity) {
        var validationResult = timeUnitValidator.validate(entity);
        if (validationResult.isValid()) {
            timeUnitRepository.update(entity);
        }
        return validationResult;
    }

    @Override
    public void deleteByGuid(String id) {
        timeUnitRepository.deleteByGuid(id);
    }

    @Override
    public void deleteAll() {
        timeUnitRepository.deleteAll();
    }
}
