package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;

import javax.swing.*;
import java.awt.*;

public final class CategoryDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final JColorChooser color = new JColorChooser(Color.BLACK);;


    private final Category category;

    public CategoryDialog(Category category) {
        this.category = category;
        // setValues();
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
    Event getEntity() {
        return null;
    }
}
