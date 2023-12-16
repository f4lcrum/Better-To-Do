package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;

import javax.swing.JTextField;

public class TimeUnitDialog extends EntityDialog<TimeUnit> {

    private final JTextField nameField = new JTextField();
    private final JTextField hourField = new JTextField();
    private final JTextField minuteField = new JTextField();

    private final TimeUnit timeUnit;

    public TimeUnitDialog(TimeUnit timeUnit, boolean edit) {
        this.timeUnit = timeUnit;
        addDialogFields();
        if (edit) {
            setDialogValues();
        }
        setHints();
    }

    private void setHints() {
        new TextPrompt("Sprint", nameField);
        new TextPrompt("4", hourField);
        new TextPrompt("30", minuteField);
    }

    private void setDialogValues() {
        nameField.setText(timeUnit.getName());
        hourField.setText(String.valueOf(timeUnit.getHours()));
        minuteField.setText(String.valueOf(timeUnit.getMinutes()));
    }

    private void addDialogFields() {
        add("Name of the unit", nameField, true);
        add("Hours unit represents", hourField, true);
        add("Minutes unit represents", minuteField, true);
    }


    @Override
    public TimeUnit getEntity() {
        return new TimeUnit(timeUnit.getGuid(), false, nameField.getText(), Long.parseLong(hourField.getText()), Long.parseLong(minuteField.getText()));
    }
}
