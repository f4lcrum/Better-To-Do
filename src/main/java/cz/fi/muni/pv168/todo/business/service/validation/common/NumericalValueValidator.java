package cz.fi.muni.pv168.todo.business.service.validation.common;

import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;

public class NumericalValueValidator<T extends Number> extends PropertyValidator<T> {

    private final T min;
    private final T max;

    public NumericalValueValidator(T min, T max, String name) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public ValidationResult validate(T value) {
        var result = new ValidationResult();
        if (min.longValue() > value.longValue() || value.longValue() > max.longValue()) {
            result.add("'%d' value is not between %d (inclusive) and %d (inclusive)"
                    .formatted(value, min, max)
            );
        }
        return result;
    }


}
