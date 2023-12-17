package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TimeUnitRenderer;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import java.time.LocalTime;

public final class TemplateDialog extends EntityDialog<Template> {

    private final PlaceholderTextField nameField = new PlaceholderTextField();
    private final PlaceholderTextField eventNameField = new PlaceholderTextField();
    private final PlaceholderTextField duration = new PlaceholderTextField();
    private final PlaceholderTextArea description = new PlaceholderTextArea();
    private final PlaceholderTextField hourField = new PlaceholderTextField();
    private final PlaceholderTextField minuteField = new PlaceholderTextField();
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<TimeUnit> timeUnitModel;

    private final Template template;

    public TemplateDialog(Template template, ListModel<Category> categoryModel, ListModel<TimeUnit> timeUnitListModel,
                          boolean edit) {
        this.template = template;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.timeUnitModel = new ComboBoxModelAdapter<>(timeUnitListModel);
        addFields();
        if (edit) {
            setValues();
        }
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

        add("Name of template", "Doctor's appointment", nameField);
        add("Name of event", "Annual doctor's visit", eventNameField);
        addOptional("Category", categoryComboBox);
        addTime("Start time: ", hourField, minuteField);
        add("Time unit count", "5", duration);
        addMandatory("Time unit", timeUnitComboBox);
        addDescription("Description", "Template for creating various doctor's appointments", description);
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
