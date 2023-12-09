package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.EitherRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.SpecialTemplateRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.SpecialTemplateValues;
import cz.fi.muni.pv168.todo.ui.renderer.TemplateRenderer;
import cz.fi.muni.pv168.todo.util.Either;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import java.awt.Font;
import java.time.LocalDate;

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
        var templateComboBox = new JComboBox<Either<Template, SpecialTemplateValues>>(templateModel);
        templateComboBox.setSelectedItem(EitherRenderer.create(new TemplateRenderer(), new SpecialTemplateRenderer()));
        templateComboBox.setRenderer(EitherRenderer.create(new TemplateRenderer(), new SpecialTemplateRenderer()));
        templateComboBox.addItemListener(e -> {
            int index = templateComboBox.getSelectedIndex();
            if (index >= 1) {
                var eitherTemplate = templateComboBox.getItemAt(index);
                if (eitherTemplate.getLeft().isPresent()) {
                    var template = eitherTemplate.getLeft().get();
                    hourField.setText(Integer.toString((template.getStartTime().getHour())));
                    minuteField.setText(Integer.toString(template.getStartTime().getMinute()));
                    categoryComboBox.setSelectedItem(template.getCategory());
                    duration.setText(Long.toString((template.getTemplateDuration())));
                }
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
