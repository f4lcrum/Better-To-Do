package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TimeUnitRenderer;

import java.awt.event.ItemEvent;
import java.time.DateTimeException;
import java.util.Date;
import java.util.Objects;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.ListModel;
import java.time.LocalTime;

public final class TemplateDialog extends EntityDialog<Template> {

    private final CategoryCrudService categoryCrudService;
    private final PlaceholderTextField nameField = new PlaceholderTextField();
    private final PlaceholderTextField eventNameField = new PlaceholderTextField();
    private final PlaceholderTextField duration = new PlaceholderTextField();
    private final PlaceholderTextArea description = new PlaceholderTextArea();
    private final PlaceholderTextField hourField = new PlaceholderTextField();
    private final PlaceholderTextField minuteField = new PlaceholderTextField();
    private final ComboBoxModel<Category> categoryModel;
    private final ComboBoxModel<TimeUnit> timeUnitModel;

    private final Template template;

    public TemplateDialog(CategoryCrudService categoryCrudService, Template template, ListModel<Category> categoryModel, ListModel<TimeUnit> timeUnitListModel,
                          boolean edit, Validator<Template> entityValidator) {
        super(Objects.requireNonNull(entityValidator));
        this.categoryCrudService = categoryCrudService;
        this.template = template;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.timeUnitModel = new ComboBoxModelAdapter<>(timeUnitListModel);
        if (edit) {
            setValues();
        }
        addFields();
    }

    private void setValues() {
        nameField.setText(template.getName());
        eventNameField.setText(template.getEventName());
        duration.setText(Long.toString(template.getDuration()));
        description.setText(template.getDescription());
        categoryModel.setSelectedItem(template.getCategory());
        timeUnitModel.setSelectedItem(template.getTimeUnit());
        hourField.setText(Integer.toString(template.getStartTime().getHour()));
        minuteField.setText(Integer.toString(template.getStartTime().getMinute()));
    }

    private void addFields() {
        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setRenderer(new CategoryRenderer());
        categoryComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (((Category) e.getItem()).isDefault()) {
                    categoryComboBox.setSelectedItem(null);
                }
            }
        });
        var timeUnitComboBox = new JComboBox<>(timeUnitModel);
        timeUnitComboBox.setRenderer(new TimeUnitRenderer());

        add("Name of template", "Doctor's appointment", nameField);
        add("Name of event", "Annual doctor's visit", eventNameField);
        addOptional("Category", categoryComboBox);
        addTime("Start time: ", hourField, minuteField);
        add("Time unit count", "5", duration);
        addMandatory("Time unit", timeUnitComboBox);
        addDescription("Description", "Template for creating various doctor's appointments", description);
        addErrorPanel();
    }

    @Override
    ValidationResult isValid() {
        var result = new ValidationResult();

        try {
            Integer.parseInt(hourField.getText());
        } catch (NumberFormatException e) {
            result.add("Incorrect field: insert integer value into hours field");
        }

        try {
            Integer.parseInt(minuteField.getText());
        } catch (NumberFormatException e) {
            result.add("Incorrect field: insert integer value into minutes field");
        }

        try {
            Integer.parseInt(duration.getText());
        } catch (NumberFormatException e) {
            result.add("Incorrect field: insert integer value into time unit count field");
        }

        try {
            LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText()));
        } catch (DateTimeException e) {
            result.add("Incorrect field: insert valid hours or minutes");
        }

        return result;
    }

    @Override
    Template getEntity() {
        return new Template(
                template.getGuid(),
                nameField.getText(),
                eventNameField.getText(),
                (categoryModel.getSelectedItem() != null ? ((Category) categoryModel.getSelectedItem()) : categoryCrudService.findDefault()),
                LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())),
                (TimeUnit) timeUnitModel.getSelectedItem(),
                Integer.parseInt(duration.getText()),
                description.getText());
    }
}
