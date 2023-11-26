package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;

import javax.swing.JColorChooser;
import java.awt.Color;

public final class CategoryDialog extends EntityDialog<Event> {

    private final PlaceholderTextField nameField = new PlaceholderTextField("Healthcare");
    private final JColorChooser color = new JColorChooser(Color.BLACK);

    private final Category category;

    public CategoryDialog(Category category) {
        this.category = category;
        // setValues();
        addFields();
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
