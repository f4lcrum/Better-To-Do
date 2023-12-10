package cz.fi.muni.pv168.todo.business.service.validation.common;

import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;

import java.time.LocalTime;

public class LocalTimeValidator extends PropertyValidator<LocalTime> {

    public LocalTimeValidator() {
        this(null);
    }

    public LocalTimeValidator(String name) {
        super(name);
    }

    @Override
    public ValidationResult validate(LocalTime value) {
        var result = new ValidationResult();
        if (0 > value.getHour() || value.getHour() > 23 || 0 > value.getMinute() || value.getMinute() > 59) {
            result.add("Time %d %d is not correct start time"
                    .formatted(value.getHour(), value.getMinute())
            );
        }
        return result;
    }
}
