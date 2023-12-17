package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

public class TemplateCrudService extends CrudServiceImplementation<Template> {

    public TemplateCrudService(Repository<Template> repository, Validator<Template> validator) {
        super(repository, validator);
    }

    @Override
    public ValidationResult create(Template newEntity) {
        var validationResult = validator.validate(newEntity);
        if (newEntity.getGuid().toString() == null || newEntity.getGuid().toString().isBlank()) {
            throw new EntityNoUUIDException("Template does not have assigned UUID");
        } else if (repository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Template with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            repository.create(newEntity);
        }
        return validationResult;
    }
}
