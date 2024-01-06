package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.listener.TemplateComboBoxItemListener;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TemplateRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TimeUnitRenderer;
import java.util.Objects;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import java.time.LocalDate;
import java.time.LocalTime;

public final class EventDialog extends EntityDialog<Event> {

    private final PlaceholderTextField nameField = new PlaceholderTextField();
    private final PlaceholderTextField duration = new PlaceholderTextField();
    private final PlaceholderTextArea description = new PlaceholderTextArea();
    private final ComboBoxModel<Category> categoryModel;
    private final PlaceholderTextField hourField = new PlaceholderTextField();
    private final PlaceholderTextField minuteField = new PlaceholderTextField();
    private final DateModel<LocalDate> dateModel = new LocalDateModel();
    private final ComboBoxModel<TimeUnit> timeUnitModel;
    private final ComboBoxModel<Template> templateModel;

    private final Event event;

    public EventDialog(Event event, ListModel<Category> categoryModel, ListModel<TimeUnit> timeUnitListModel,
                       ListModel<Template> templateListModel, boolean edit, Validator<Event> entityValidator) {
        super(Objects.requireNonNull(entityValidator));
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.timeUnitModel = new ComboBoxModelAdapter<>(timeUnitListModel);
        this.templateModel = new ComboBoxModelAdapter<>(templateListModel);
        if (edit) {
            setValues();
        }
        addFields();
    }

    private void setValues() {
        nameField.setText(event.getName());
        duration.setText(String.valueOf(event.getDuration()));
        description.setText(event.getDescription());
        categoryModel.setSelectedItem(event.getCategory());
        dateModel.setValue(event.getDate());
        timeUnitModel.setSelectedItem(event.getTimeUnit());
        hourField.setText(Integer.toString(event.getStartTime().getHour()));
        minuteField.setText(Integer.toString(event.getStartTime().getMinute()));
    }

    private void addFields() {
        var categoryComboBox = new JComboBox<>(categoryModel);
        categoryComboBox.setRenderer(new CategoryRenderer());
        var timeUnitComboBox = new JComboBox<>(timeUnitModel);
        timeUnitComboBox.setRenderer(new TimeUnitRenderer());
        var templateComboBox = new JComboBox<>(templateModel);
        templateComboBox.setRenderer(new TemplateRenderer());
        templateComboBox.addItemListener(new TemplateComboBoxItemListener(templateComboBox, this));

        add("Name of the event", "Party at John's", nameField);
        addOptional("Template", templateComboBox);
        addMandatory("Start date of the event", new JDatePicker(dateModel));
        addTime("Start time of event: ", hourField, minuteField);
        addOptional("Category", categoryComboBox);
        addMandatory("Time unit", timeUnitComboBox);
        add("Time unit count", "5", duration);;
        addDescription("Description", "A weekend party at John's place.", description);
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

        if (dateModel.getValue() == null) {
            result.add("Incorrect field:; insert date value into date field");
        }

        return result;
    }

    @Override
    Event getEntity() {
        return new Event(
                event.getGuid(),
                nameField.getText(),
                (Category) categoryModel.getSelectedItem(),
                dateModel.getValue(),
                LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())),
                (TimeUnit) timeUnitModel.getSelectedItem(),
                Integer.parseInt(duration.getText()),
                description.getText()
        );
    }

    public void SetTemplate(Template template) {
        nameField.setText(template.getEventName());
        hourField.setText(Integer.toString((template.getStartTime().getHour())));
        minuteField.setText(Integer.toString(template.getStartTime().getMinute()));
        categoryModel.setSelectedItem(template.getCategory());
        timeUnitModel.setSelectedItem(template.getTimeUnit());
        duration.setText(Integer.toString(template.getDuration()));
    }
}
