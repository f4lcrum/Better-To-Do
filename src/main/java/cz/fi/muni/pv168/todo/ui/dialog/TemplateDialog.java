package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.Status;
import cz.fi.muni.pv168.todo.entity.Template;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.StatusRenderer;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.MINUTES;

public final class TemplateDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final JTextField duration = new JTextField();
    private final JTextArea description = new JTextArea();
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<Status> statusModel;
    private final DateModel<LocalDate> dateModel = new LocalDateModel();

    private final Template template;

    public TemplateDialog(Template template, ListModel<Category> categoryModel, ListModel<Status> statusModel) {
        this.template = template;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.statusModel = new ComboBoxModelAdapter<>(statusModel);
        // setValues();
        addFields();
        setHints();
    }

    private void setHints() {
        var nameHint = new TextPrompt(template.getName(), nameField, TextPrompt.Show.FOCUS_LOST);
        var durationHint = new TextPrompt(String.format("%s minutes", template.getTemplateDuration()), duration, TextPrompt.Show.FOCUS_LOST);
        var descriptionHint = new TextPrompt(template.getDescription(), description, TextPrompt.Show.FOCUS_LOST);
        nameHint.changeAlpha(0.5f);
        durationHint.changeAlpha(0.5f);
        descriptionHint.changeAlpha(0.5f);
        nameHint.changeStyle(Font.ITALIC);
        durationHint.changeStyle(Font.ITALIC);
        descriptionHint.changeStyle(Font.ITALIC);
    }

    private void setValues() {
        nameField.setText(template.getName());
        duration.setText(String.valueOf(template.getStartTime().until(template.getEndTime(), MINUTES)));
        description.setText(template.getDescription());
        categoryModel.setSelectedItem(template.getCategory());
    }

    private void addFields() {
        var statusComboBox = new JComboBox<>(statusModel);
        statusComboBox.setSelectedItem(new StatusRenderer());

        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setSelectedItem(new CategoryRenderer());

        add("Name of template", nameField, true);
        add("Date of event", new JDatePicker(dateModel), true);
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
