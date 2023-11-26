package cz.fi.muni.pv168.todo.ui.dialog;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.Status;
import cz.fi.muni.pv168.todo.entity.Template;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import cz.fi.muni.pv168.todo.ui.model.ComboBoxModelAdapter;
import cz.fi.muni.pv168.todo.ui.model.LocalDateModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.StatusRenderer;
import static java.time.temporal.ChronoUnit.MINUTES;
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

public final class TemplateDialog extends EntityDialog<Event> {

    private final PlaceholderTextField nameField = new PlaceholderTextField("Doctor's visit");
    private final PlaceholderTextField duration = new PlaceholderTextField("5");
    private final PlaceholderTextArea description = new PlaceholderTextArea("A short regular annual visit to my doctor.");
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
