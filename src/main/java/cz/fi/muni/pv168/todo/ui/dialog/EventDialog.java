package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderLabel;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import java.time.LocalDate;

public final class EventDialog extends EntityDialog<Event> {

    private final PlaceholderTextField nameField = new PlaceholderTextField();
    private final PlaceholderTextField duration = new PlaceholderTextField();
    private final PlaceholderTextArea description = new PlaceholderTextArea();
    private final ComboBoxModel<Category> categoryModel;
    private final JTextField hourField = new JTextField();
    private final JTextField minuteField = new JTextField();
    private final DateModel<LocalDate> dateModel = new LocalDateModel();

    private final Event event;

    public EventDialog(Event event, ListModel<Category> categoryModel) {
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        // setValues();
        addFields();
        setHints();
    }

    private void setHints() {
        var namePlaceholder = new PlaceholderLabel("Doctor's visit");
        var durationPlaceholder = new PlaceholderLabel("5");
        var descriptionPlaceholder = new PlaceholderLabel("A short regular annual visit to my doctor.");
        nameField.setPlaceholder(namePlaceholder);
        duration.setPlaceholder(durationPlaceholder);
        description.setPlaceholder(descriptionPlaceholder);
        nameField.add(namePlaceholder);
        duration.add(durationPlaceholder);
        description.add(descriptionPlaceholder);
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

        add("Name of the event", nameField, true);
        add("Start date of the event", new JDatePicker(dateModel), true);
        addTime("Start time of event: ", hourField, minuteField);
        add("Category", categoryComboBox, false);
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
