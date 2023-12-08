package cz.fi.muni.pv168.todo.business.service.validation;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.service.validation.common.StringLengthValidator;

import java.util.List;

/**
 * Department entity {@link Validator}
 */
public class TemplateValidator implements Validator<Template> {
    @Override
    public ValidationResult validate(Template template) {
        var validators = List.of(
                Validator.extracting(Template::getName, new StringLengthValidator(2, 150, "Template name")),
                Validator.extracting(Template::getName, new StringLengthValidator(2, 400, "Template description"))
        );

        return Validator.compose(validators).validate(template);
    }
}
