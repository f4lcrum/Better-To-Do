package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TimeUnitRenderer;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import java.time.LocalTime;

public final class TemplateDialog extends EntityDialog<Template> {

    private final JTextField nameField = new JTextField();
    private final JTextField eventNameField = new JTextField();
    private final JTextField duration = new JTextField();
    private final JTextArea description = new JTextArea();
    private final JTextField hourField = new JTextField();
    private final JTextField minuteField = new JTextField();
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<TimeUnit> timeUnitModel;

    private final Template template;

    public TemplateDialog(Template template, ListModel<Category> categoryModel, ListModel<TimeUnit> timeUnitListModel,
                          boolean edit) {
        this.template = template;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.timeUnitModel = new ComboBoxModelAdapter<>(timeUnitListModel);
        addFields();
        setHints();
        if (edit) {
            setValues();
        }
    }

    private void setHints() {
        new TextPrompt("Doctor's appointment", nameField);
        new TextPrompt("Annual doctor's visit", eventNameField);
        new TextPrompt("5", duration);
        new TextPrompt("Template for creating various doctor's appointments", description);
        new TextPrompt("5", hourField);
        new TextPrompt("30", minuteField);
    }

    private void setValues() {
        nameField.setText(template.getName());
        eventNameField.setText(template.getEventName());
        duration.setText(Long.toString(template.getTimeUnitCount()));
        description.setText(template.getDescription());
        categoryModel.setSelectedItem(template.getCategory());
        timeUnitModel.setSelectedItem(template.getTimeUnit());
        hourField.setText(Integer.toString(template.getStartTime().getHour()));
        minuteField.setText(Integer.toString(template.getStartTime().getMinute()));
    }

    private void addFields() {
        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setRenderer(new CategoryRenderer());
        var timeUnitComboBox = new JComboBox<>(timeUnitModel);
        timeUnitComboBox.setRenderer(new TimeUnitRenderer());

        add("Name of template", nameField, true);
        add("Name of event", eventNameField, true);
        add("Category", categoryComboBox, false);
        addTime("Start time: ", hourField, minuteField);
        add("Time unit", timeUnitComboBox, true);
        add("Time unit count", duration, true);
        description.setLineWrap(true);
        JScrollPane descriptionPane = new JScrollPane(description);
        addDescritpion("Description", descriptionPane);
    }

    @Override
    Template getEntity() {
        return new Template(
                template.getGuid(),
                nameField.getText(),
                eventNameField.getText(),
                (Category) categoryModel.getSelectedItem(),
                LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())),
                (TimeUnit) timeUnitModel.getSelectedItem(),
                Integer.parseInt(duration.getText()),
                description.getText());
    }
}
