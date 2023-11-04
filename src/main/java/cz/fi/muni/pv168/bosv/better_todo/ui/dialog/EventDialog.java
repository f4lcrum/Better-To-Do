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
import java.lang.reflect.Array;
import java.time.LocalDate;

public final class EventDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final JTextField duration = new JTextField();
    private final JTextField description = new JTextField();
    private final ComboBoxModel<Category> categoryModel;
    private final JTextField hourField = new JTextField();
    private final JTextField minuteField = new JTextField();
    private final DateModel<LocalDate> dateModel = new LocalDateModel();

    private final Event event;

    public EventDialog(Event event, ListModel<Category> categoryModel) {
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        setValues();
        addFields();
    }

    private void setValues() {
        nameField.setText(event.getName());
        duration.setText(String.valueOf(event.getEventDuration()));
        description.setText(event.getDescription());
        categoryModel.setSelectedItem(event.getCategory());
        dateModel.setValue(event.getDate());
        hourField.setText(Integer.toString(event.getStartTime().getHour()));
        minuteField.setText(Integer.toString(event.getStartTime().getMinute()));
    }

    private void addFields() {

        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setSelectedItem(new CategoryRenderer());
        add("Name of event: ", nameField);
        add("Date of event: ", new JDatePicker(dateModel));
        add("Start time of event: ", hourField, minuteField);
        add("Category: ", categoryComboBox);
        add("Duration: ", duration);
        add("Description: ", description);

    }

    @Override
    Event getEntity() {
        return null;
    }
}
