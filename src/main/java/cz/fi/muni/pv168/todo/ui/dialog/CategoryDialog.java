package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.business.entity.Category;

import javax.swing.JColorChooser;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;

public final class CategoryDialog extends EntityDialog<Category> {

    private final JTextField nameField = new JTextField();
    private final JColorChooser color = new JColorChooser(Color.BLACK);

    private final Category category;

    public CategoryDialog(Category category, boolean edit) {
        this.category = category;
        addFields();
        if (edit) {
            setValues();
        }
        setHints();
    }

    private void setHints() {
        var nameHint = new TextPrompt("School", nameField, TextPrompt.Show.FOCUS_LOST);
        nameHint.changeAlpha(0.5f);
        nameHint.changeStyle(Font.ITALIC);
    }

    private void setValues() {
        nameField.setText(category.getName());
    }

    private void addFields() {
        add("Name of category: ", nameField, true);
        add("Color: ", color, true);
    }

    @Override
    Category getEntity() {
        return new Category(category.getGuid(), nameField.getText(), color.getColor());
    }
}
