package cz.fi.muni.pv168.bosv.better_todo.ui.dialog;


import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.entity.Template;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.*;
import cz.fi.muni.pv168.bosv.better_todo.util.Either;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Optional;

public final class EventDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final JTextField duration = new JTextField();
    private final JTextArea description = new JTextArea();
    private final ComboBoxModel<Category> categoryModel;
    private final JTextField hourField = new JTextField();
    private final JTextField minuteField = new JTextField();
    private final DateModel<LocalDate> dateModel = new LocalDateModel();
    private final ComboBoxModel<Either<Template, SpecialTemplateValues>> templateModel;

    private final Event event;

    public EventDialog(Event event, ListModel<Category> categoryModel, ListModel<Either<Template, SpecialTemplateValues>> templateModel) {
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.templateModel = new ComboBoxModelAdapter<>(templateModel);
        // setValues();
        addFields();
        setHints();
    }

    private void setHints() {
        var nameHint = new TextPrompt(event.getName(), nameField, TextPrompt.Show.FOCUS_LOST);
        var durationHint = new TextPrompt(String.format("%s minutes", event.getEventDuration()), duration, TextPrompt.Show.FOCUS_LOST);
        var descriptionHint = new TextPrompt(event.getDescription(), description, TextPrompt.Show.FOCUS_LOST);
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
        categoryModel.setSelectedItem(event.getCategory());
        dateModel.setValue(event.getDate());
        hourField.setText(Integer.toString(event.getStartTime().getHour()));
        minuteField.setText(Integer.toString(event.getStartTime().getMinute()));
    }

    private void addFields() {
        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setSelectedItem(new CategoryRenderer());
        var templateComboBox = new JComboBox<>(templateModel);
        templateComboBox.setSelectedItem(EitherRenderer.create(new TemplateRenderer(), new SpecialTemplateRenderer()));
        templateComboBox.setRenderer(EitherRenderer.create(new TemplateRenderer(), new SpecialTemplateRenderer()));
        templateComboBox.addItemListener(e -> {
            if (templateComboBox.getSelectedIndex() == 0) {
                return;
            }
            Object selectedItem = templateComboBox.getSelectedItem();
            if (selectedItem instanceof Either) {
                var left = ((Either<?, ?>) selectedItem).getLeft();
                if (left.isEmpty()) {
                    return;
                }
                Object template = left.get();
                if (!(template instanceof Template)) {
                    return;
                }
                hourField.setText(Integer.toString(((Template) template).getStartTime().getHour()));
                minuteField.setText(Integer.toString(((Template) template).getStartTime().getMinute()));
                categoryComboBox.setSelectedItem(((Template) template).getCategory());
                duration.setText(Long.toString((((Template) template).getTemplateDuration())));
            }
        });

        add("Name of the event", nameField, true);
        add("Template to use: ", templateComboBox, false);
        add("Start date of the event", new JDatePicker(dateModel), true);
        addTime("Start time of event", hourField, minuteField);
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
