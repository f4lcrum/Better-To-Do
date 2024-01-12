package cz.fi.muni.pv168.todo.business.service.validation;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.service.validation.common.NumericalValueValidator;
import cz.fi.muni.pv168.todo.business.service.validation.common.StringLengthValidator;

import java.util.List;

/**
 * Employee entity {@link Validator}.
 * @author Vojtech Sassmann
 */
public class EventValidator implements Validator<Event> {

    @Override
    public ValidationResult validate(Event model) {
        var validators = List.of(
                Validator.extracting(Event::getName, new StringLengthValidator(2, 128, "Event name")),
                Validator.extracting(Event::getDescription, new StringLengthValidator(0, 1024, "Event description")),
                Validator.extracting(Event::getDuration, new NumericalValueValidator<>(1L, Long.MAX_VALUE, "Event duration"))
        );

        return Validator.compose(validators).validate(model);
    }
}

