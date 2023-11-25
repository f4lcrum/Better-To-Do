package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.CategoryColor;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;

import javax.swing.ComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public final class CategoryDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final ComboBoxModel<Color> categoryColorModel;

    private final Category category;

    public CategoryDialog(Category category, ListModel<Color> categoryColor) {
        this.category = category;
        this.categoryColorModel = new ComboBoxModelAdapter<>(categoryColor);
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
        var categoryColorComboBox = new JComboBox<>(categoryColorModel);
        var clrRenderer = new CategoryColourRenderer();
        categoryColorComboBox.setSelectedItem(clrRenderer);

        add("Name of category: ", nameField, true);
        add("Category Color", categoryColorComboBox, true);
    }

    @Override
    Event getEntity() {
        return null;
    }
}
