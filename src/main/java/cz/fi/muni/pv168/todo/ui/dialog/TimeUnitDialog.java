package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;

import cz.fi.muni.pv168.todo.business.service.validation.ValidationResult;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Objects;
import javax.swing.JTextField;

public class TimeUnitDialog extends EntityDialog<TimeUnit> {

    private final PlaceholderTextField nameField = new PlaceholderTextField();
    private final PlaceholderTextField hourField = new PlaceholderTextField();
    private final PlaceholderTextField minuteField = new PlaceholderTextField();

    private final TimeUnit timeUnit;

    public TimeUnitDialog(TimeUnit timeUnit, boolean edit, Validator<TimeUnit> entityValidator) {
        super(Objects.requireNonNull(entityValidator));
        this.timeUnit = timeUnit;
        if (edit) {
            setDialogValues();
        }
        addDialogFields();
    }

    private void setDialogValues() {
        nameField.setText(timeUnit.getName());
        hourField.setText(String.valueOf(timeUnit.getHours()));
        minuteField.setText(String.valueOf(timeUnit.getMinutes()));
    }

    private void addDialogFields() {
        add("Name of the unit", "Sprint", nameField);
        add("Hours unit represents", "4", hourField);
        add("Minutes unit represents", "30", minuteField);
        addErrorPanel();
    }

    @Override
    ValidationResult isValid() {
        var result = new ValidationResult();

        try {
            Long.parseLong(hourField.getText());
        } catch (NumberFormatException e) {
            result.add("Incorrect field: insert integer value into hours field");
        }

        try {
            Long.parseLong(minuteField.getText());
        } catch (NumberFormatException e) {
            result.add("Incorrect field: insert integer value into minutes field");
        }

        try {
            LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText()));
        } catch (DateTimeException e) {
            result.add("Incorrect field: insert valid hours or minutes");
        }

        return result;
    }

    @Override
    public TimeUnit getEntity() {
        return new TimeUnit(timeUnit.getGuid(), false, nameField.getText(), Long.parseLong(hourField.getText()), Long.parseLong(minuteField.getText()));
    }
}
