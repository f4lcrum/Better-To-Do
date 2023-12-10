package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.listener.TemplateComboBoxItemListnener;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TemplateRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.TimeUnitRenderer;
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
import java.time.LocalTime;

public final class EventDialog extends EntityDialog<Event> {

    private final JTextField nameField = new JTextField();
    private final JTextField duration = new JTextField();
    private final JTextArea description = new JTextArea();
    private final ComboBoxModel<Category> categoryModel;
    private final JTextField hourField = new JTextField();
    private final JTextField minuteField = new JTextField();
    private final DateModel<LocalDate> dateModel = new LocalDateModel();
    private final ComboBoxModel<TimeUnit> timeUnitModel;
    private final ComboBoxModel<Template> templateModel;

    private final Event event;

    public EventDialog(Event event, ListModel<Category> categoryModel, ListModel<TimeUnit> timeUnitListModel,
                       ListModel<Template> templateListModel, boolean edit) {
        this.event = event;
        this.categoryModel = new ComboBoxModelAdapter<>(categoryModel);
        this.timeUnitModel = new ComboBoxModelAdapter<>(timeUnitListModel);
        this.templateModel = new ComboBoxModelAdapter<>(templateListModel);
        addFields();
        setHints();
        if (edit) {
            setValues();
        }
    }

    private void setHints() {
        var nameHint = new TextPrompt("Party at John's", nameField, TextPrompt.Show.FOCUS_LOST);
        var durationHint = new TextPrompt("5", duration, TextPrompt.Show.FOCUS_LOST);
        var descriptionHint = new TextPrompt("A weekend party at John's place.", description, TextPrompt.Show.FOCUS_LOST);
        nameHint.changeAlpha(0.5f);
        durationHint.changeAlpha(0.5f);
        descriptionHint.changeAlpha(0.5f);
        nameHint.changeStyle(Font.ITALIC);
        durationHint.changeStyle(Font.ITALIC);
        descriptionHint.changeStyle(Font.ITALIC);
    }

    private void setValues() {
        nameField.setText(event.getName());
        duration.setText(String.valueOf(event.getTimeUnitCount()));
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
        templateComboBox.addItemListener(new TemplateComboBoxItemListnener(templateComboBox, this));

        add("Name of the event", nameField, true);
        add("Template", templateComboBox, false);
        add("Start date of the event", new JDatePicker(dateModel), true);
        addTime("Start time of event: ", hourField, minuteField);
        add("Category", categoryComboBox, false);
        add("Time unit", timeUnitComboBox, true);
        add("Time unit count", duration, true);
        description.setLineWrap(true);
        JScrollPane descriptionPane = new JScrollPane(description);
        addDescritpion("Description", descriptionPane);
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
        duration.setText(Integer.toString(template.getTimeUnitCount()));
    }
}
