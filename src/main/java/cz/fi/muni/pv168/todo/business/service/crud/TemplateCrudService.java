package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import java.util.List;

public class TemplateCrudService implements CrudService<Template> {

    private final Repository<Template> templateRepository;
    private final Validator<Template> templateValidator;

    public TemplateCrudService(Repository<Template> templateRepository,
                               Validator<Template> templateValidator) {
        this.templateRepository = templateRepository;
        this.templateValidator = templateValidator;
    }

    @Override
    public List<Template> findAll() {
        return templateRepository.findAll();
    }

    @Override
    public ValidationResult create(Template newEntity) {
        var validationResult = templateValidator.validate(newEntity);
        if (templateRepository.existsByGuid(newEntity.getGuid().toString())) {
            throw new EntityAlreadyExistsException("Template with given id already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            templateRepository.create(newEntity);
        }
        return validationResult;
    }

    @Override
    public ValidationResult update(Template entity) {
        var validationResult = templateValidator.validate(entity);
        if (validationResult.isValid()) {
            templateRepository.update(entity);
        }
        return validationResult;
    }

    @Override
    public void deleteByGuid(String id) {
        templateRepository.deleteByGuid(id);
    }

    @Override
    public void deleteAll() {
        templateRepository.deleteAll();
    }
}
