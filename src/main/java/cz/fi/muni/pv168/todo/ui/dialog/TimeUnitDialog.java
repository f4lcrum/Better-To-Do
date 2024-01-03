package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;

public class TimeUnitDialog extends EntityDialog<TimeUnit> {

    private final PlaceholderTextField nameField = new PlaceholderTextField();
    private final PlaceholderTextField hourField = new PlaceholderTextField();
    private final PlaceholderTextField minuteField = new PlaceholderTextField();

    private final TimeUnit timeUnit;

    public TimeUnitDialog(TimeUnit timeUnit, boolean edit) {
        this.timeUnit = timeUnit;
        addDialogFields();
        if (edit) {
            setDialogValues();
        }
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
    }


    @Override
    public TimeUnit getEntity() {
        return new TimeUnit(timeUnit.getGuid(), nameField.getText(), Long.parseLong(hourField.getText()), Long.parseLong(minuteField.getText()));
    }
}
