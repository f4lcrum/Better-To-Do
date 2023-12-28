package cz.fi.muni.pv168.todo.business.service.validation.common;

import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;

public class IntFieldValidator extends PropertyValidator<String> {

    public IntFieldValidator() {
        this(null);
    }

    public IntFieldValidator(String name) {
        super(name);
    }


    @Override
    public ValidationResult validate(String value) {
        var result = new ValidationResult();

        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            result.add("Incorrect field: insert integer value");
        }

        return result;
    }

    public ValidationResult validate(String value, String fieldName) {
        var result = new ValidationResult();

        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            result.add(String.format("Incorrect field: insert integer value into %s field", fieldName));
        }

        return result;
    }

}
