package cz.fi.muni.pv168.todo.business.service.validation;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.validation.common.StringLengthValidator;

import java.util.List;

/**
 * Employee entity {@link Validator}.
 * @author Vojtech Sassmann
 */
public class CategoryValidator implements Validator<Category> {

    @Override
    public ValidationResult validate(Category model) {
        var validators = List.of(
                Validator.extracting(Category::getName, new StringLengthValidator(2, 150, "Category name"))
        );

        return Validator.compose(validators).validate(model);
    }
}
