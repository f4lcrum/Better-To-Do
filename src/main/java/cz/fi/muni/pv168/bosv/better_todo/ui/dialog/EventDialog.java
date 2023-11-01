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
import java.time.LocalDate;

public final class EventDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final JTextField duration = new JTextField();
    private final JTextField description = new JTextField();
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<Status> statusModel;
    private final DateModel<LocalDate> dateModel = new LocalDateModel();

    private final Event event;

    public EventDialog(Event event, ListModel<Category> categoryModel, ListModel<Status> statusModel) {
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.statusModel = new ComboBoxModelAdapter<>(statusModel);
        setValues();
        addFields();
    }

    private void setValues() {
        nameField.setText(event.getName());
        duration.setText(String.valueOf(event.getEventDuration()));
        description.setText(event.getDescription());
        statusModel.setSelectedItem(event.getStatus());
        categoryModel.setSelectedItem(event.getCategory());
        dateModel.setValue(event.getDate());
    }

    private void addFields() {
        var statusComboBox = new JComboBox<>(statusModel);
        statusComboBox.setSelectedItem(new StatusRenderer());

        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setSelectedItem(new CategoryRenderer());

        add("Name of the event: ", nameField);
        add("Start date: ", new JDatePicker(dateModel));
        add("Category: ", categoryComboBox);
        add("Status: ", statusComboBox);
        add("Duration: ", duration);
        add("Description: ", description);
    }

    @Override
    Event getEntity() {
        return null;
    }
}
