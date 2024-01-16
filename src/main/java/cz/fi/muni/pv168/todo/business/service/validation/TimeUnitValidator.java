package cz.fi.muni.pv168.todo.business.service.validation;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.common.NumericalValueValidator;
import cz.fi.muni.pv168.todo.business.service.validation.common.StringLengthValidator;

import java.util.List;

/**
 * @author Vojtech Sassmann
 */
public class TimeUnitValidator implements Validator<TimeUnit> {

    @Override
    public ValidationResult validate(TimeUnit model) {
        var validators = List.of(
                Validator.extracting(TimeUnit::getName, new StringLengthValidator(2, 128, "Time unit name")),
                Validator.extracting(TimeUnit::getHours, new NumericalValueValidator<>(0L, Long.MAX_VALUE, "Time unit hours")),
                Validator.extracting(TimeUnit::getMinutes, new NumericalValueValidator<>(0L, Long.MAX_VALUE, "Time unit minutes")),
                Validator.extracting(TimeUnit::getMinutesDuration, new NumericalValueValidator<>(1L, Long.MAX_VALUE, "TimeUnit duration"))
        );

        return Validator.compose(validators).validate(model);
    }
}
