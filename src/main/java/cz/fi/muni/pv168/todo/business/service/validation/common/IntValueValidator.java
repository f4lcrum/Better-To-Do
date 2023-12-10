package cz.fi.muni.pv168.todo.business.service.validation.common;

import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;

public class IntValueValidator extends PropertyValidator<Integer> {

    private final long min;

    private final long max;

    public IntValueValidator(long min, long max) {
        this(min, max, null);
    }

    public IntValueValidator(long min, long max, String name) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public ValidationResult validate(Integer value) {
        var result = new ValidationResult();
        if (min > value || value > max) {
            result.add("'%d' value is not between %d (exclusive) and %d (exclusive)"
                    .formatted(value, min, max)
            );
        }
        return result;
    }
}
