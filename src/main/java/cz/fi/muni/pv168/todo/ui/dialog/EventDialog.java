package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.action.CreateTemplateFromEventAction;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import cz.fi.muni.pv168.todo.ui.listener.TemplateComboBoxItemListener;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TemplateRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TimeUnitRenderer;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import java.awt.event.ItemEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public final class EventDialog extends EntityDialog<Event> {

    private final CategoryCrudService categoryCrudService;
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
    private int result;

    public EventDialog(CategoryCrudService categoryCrudService, Event event, ListModel<Category> categoryModel, ListModel<TimeUnit> timeUnitListModel,
                       ListModel<Template> templateListModel, boolean edit, Validator<Event> entityValidator, CreateTemplateFromEventAction action) {
        super(Objects.requireNonNull(entityValidator));
        this.categoryCrudService = categoryCrudService;
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.timeUnitModel = new ComboBoxModelAdapter<>(timeUnitListModel);
        this.templateModel = new ComboBoxModelAdapter<>(templateListModel);
        addFields(action);
        if (edit) {
            setValues();
        }

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

    private void addFields(CreateTemplateFromEventAction action) {
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
        var templateComboBox = new JComboBox<>(templateModel);
        templateComboBox.setRenderer(new TemplateRenderer());
        templateComboBox.addItemListener(new TemplateComboBoxItemListener(templateComboBox, this));

        add("Name of the event", "Party at John's", nameField);
        addOptional("Template", templateComboBox);
        addMandatory("Start date of the event", new JDatePicker(dateModel));
        addTime("Start time of event: ", hourField, minuteField);
        addOptional("Category", categoryComboBox);
        addMandatory("Time unit", timeUnitComboBox);
        add("Time unit count", "5", duration);
        addDescription("Description", "A weekend party at John's place.", description);
        addTemplateFromEventButton(action);
        addErrorPanel();
    }

    private void addTemplateFromEventButton(CreateTemplateFromEventAction action) {
        action.setDialog(this);
        action.setEnabled(true);
        panel.add(new JLabel(""));
        panel.add(new JButton(action), "span, al center center");
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
            result.add("Incorrect field: insert date value into date field");
        }

        try {
            LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText()));
        } catch (DateTimeException e) {
            result.add("Incorrect field: insert valid hours or minutes");
        } catch (NumberFormatException e) {

        }

        if (timeUnitModel.getSelectedItem() == null) {
            result.add("Incorrect field: select time unit value");
        }

        return result;
    }

    public Event validateAndGetEvent(JComponent parentComponent, String title) {
        var validateFields = isValid();
        if (validateFields.isValid()) {
            var entity = getEntity();
            var validation = entityValidator.validate(entity);
            if (validation.isValid()) {
                return entity;
            }
            showErrorMessages(validation.getValidationErrors());
        }
        else {
            showErrorMessages(validateFields.getValidationErrors());
        }
        result = showOptionDialog(parentComponent, title);
        return null;
    }

    @Override
    Event getEntity() {
        return new Event(
                event.getGuid(),
                nameField.getText(),
                (categoryModel.getSelectedItem() != null ? ((Category) categoryModel.getSelectedItem()) : categoryCrudService.findDefault()),
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

    @Override
    public Optional<Event> show(JComponent parentComponent, String title) {
        result = JOptionPane.showOptionDialog(parentComponent, panel, title, OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);

        while (result == OK_OPTION) {
            var validateFields = isValid();
            if (validateFields.isValid()) {
                var entity = getEntity();
                var validation = entityValidator.validate(entity);

                if (validation.isValid()) {
                    return Optional.of(entity);
                }

                showErrorMessages(validation.getValidationErrors());
            } else {
                showErrorMessages(validateFields.getValidationErrors());
            }
            result = showOptionDialog(parentComponent, title);
        }

        return Optional.empty();
    }
}
