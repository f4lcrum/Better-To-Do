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
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public final class EventDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final JTextField duration = new JTextField();
    private final JTextArea description = new JTextArea();
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<Status> statusModel;
    private final DateModel<LocalDate> dateModel = new LocalDateModel();

    private final Event event;

    public EventDialog(Event event, ListModel<Category> categoryModel, ListModel<Status> statusModel) {
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.statusModel = new ComboBoxModelAdapter<>(statusModel);
        // setValues();
        addFields();
        setHints();
    }

    private void setHints() {
        var nameHint = new TextPrompt(String.format("i.e. %s", event.getName()), nameField, TextPrompt.Show.FOCUS_LOST);
        var durationHint = new TextPrompt(String.format("i.e. %s", event.getEventDuration()), duration, TextPrompt.Show.FOCUS_LOST);
        var descriptionHint = new TextPrompt(String.format("i.e. %s", event.getDescription()), description, TextPrompt.Show.FOCUS_LOST);
        nameHint.changeAlpha(0.5f);
        durationHint.changeAlpha(0.5f);
        descriptionHint.changeAlpha(0.5f);
        nameHint.changeStyle(Font.ITALIC);
        durationHint.changeStyle(Font.ITALIC);
        descriptionHint.changeStyle(Font.ITALIC);
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

        add("Name of the event", nameField, true);
        add("Start date", new JDatePicker(dateModel), true);
        add("Category", categoryComboBox, false);
        add("Status", statusComboBox, false);
        add("Duration", duration, true);
        description.setLineWrap(true);
        JScrollPane descriptionPane = new JScrollPane(description);
        addDescritpion("Description", descriptionPane);
    }

    @Override
    Event getEntity() {
        return null;
    }
}
