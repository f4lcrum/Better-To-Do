package cz.fi.muni.pv168.todo.business.service.validation;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.service.validation.common.LocalTimeValidator;
import cz.fi.muni.pv168.todo.business.service.validation.common.NumericalValueValidator;
import cz.fi.muni.pv168.todo.business.service.validation.common.StringLengthValidator;

import java.util.List;

/**
 * Template entity {@link Validator}
 */
public class TemplateValidator implements Validator<Template> {

    @Override
    public ValidationResult validate(Template template) {

        var validators = List.of(
                Validator.extracting(Template::getName, new StringLengthValidator(2, 128, "Template name")),
                Validator.extracting(Template::getEventName, new StringLengthValidator(2, 128, "Template event name")),
                Validator.extracting(Template::getName, new StringLengthValidator(0, 1024, "Template description")),
                Validator.extracting(Template::getStartTime, new LocalTimeValidator("Template start time")),
                Validator.extracting(Template::getDuration, new NumericalValueValidator<>(0, Integer.MAX_VALUE, "Template duration"))
        );

        return Validator.compose(validators).validate(template);
    }
}
