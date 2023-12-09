package cz.fi.muni.pv168.todo.business.service.validation;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.business.service.validation.common.StringLengthValidator;

import java.util.List;

/**
 * @author Vojtech Sassmann
 */
public class TimeUnitValidator implements Validator<TimeUnit> {

    @Override
    public ValidationResult validate(TimeUnit model) {
        var validators = List.of(
                Validator.extracting(TimeUnit::getName, new StringLengthValidator(2, 128, "Time unit name"))
        );

        return Validator.compose(validators).validate(model);
    }
}
