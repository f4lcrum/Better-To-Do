package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;

import javax.swing.JColorChooser;
import java.awt.Color;

public final class CategoryDialog extends EntityDialog<Category> {

    private final PlaceholderTextField nameField = new PlaceholderTextField();
    private final JColorChooser color = new JColorChooser(Color.BLACK);

    private final Category category;

    public CategoryDialog(Category category, boolean edit) {
        this.category = category;
        if (edit) {
            setValues();
        }
        addFields();
    }

    private void setValues() {
        nameField.setText(category.getName());
    }

    private void addFields() {
        add("Name of category: ", "School", nameField);
        addMandatory("Color: ", color);
    }

    @Override
    Category getEntity() {
        return new Category(category.getGuid(), nameField.getText(), color.getColor());
    }
}
