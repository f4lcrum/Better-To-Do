package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;

import javax.swing.JColorChooser;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;

public final class CategoryDialog extends EntityDialog<Category> {

    private final JTextField nameField = new JTextField();
    private final JColorChooser color = new JColorChooser(Color.BLACK);

    private final Category category;

    public CategoryDialog(Category category) {
        this.category = category;
        setValues();
        addFields();
        setHints();
    }

    private void setHints() {
        var nameHint = new TextPrompt(category.getName(), nameField, TextPrompt.Show.FOCUS_LOST);
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
        return new Category(category.getId(), nameField.getText(), color.getColor());
    }
}
