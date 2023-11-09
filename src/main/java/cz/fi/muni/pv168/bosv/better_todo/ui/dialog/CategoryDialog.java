package cz.fi.muni.pv168.bosv.better_todo.ui.dialog;


import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.StatusRenderer;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public final class CategoryDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    // private final Color color = new LocalDateModel();


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

        add("Name of category", nameField, true);
        // add("Color: ", description);
    }

    @Override
    Event getEntity() {
        return null;
    }
}
