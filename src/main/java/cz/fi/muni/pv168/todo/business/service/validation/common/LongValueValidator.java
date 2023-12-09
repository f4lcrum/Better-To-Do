package cz.fi.muni.pv168.todo.business.service.validation.common;

import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;

public final class LongValueValidator extends PropertyValidator<Long> {
    private final long min;

    private final long max;

    public LongValueValidator(long min, long max) {
        this(min, max, null);
    }

    public LongValueValidator(long min, long max, String name) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public ValidationResult validate(Long value) {
        var result = new ValidationResult();
        if (min > value || value > max) {
            result.add("'%d' value is not between %d (inclusive) and %d (inclusive)"
                    .formatted(value, min, max)
            );
        }
        return result;
    }
}
