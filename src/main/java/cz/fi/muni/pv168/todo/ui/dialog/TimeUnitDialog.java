package cz.fi.muni.pv168.todo.ui.dialog;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;

import javax.swing.JTextField;
import java.awt.Font;

public class TimeUnitDialog extends EntityDialog<TimeUnit> {

    private final JTextField nameField = new JTextField();
    private final JTextField hourField = new JTextField();
    private final JTextField minuteField = new JTextField();

    private final TimeUnit timeUnit;

    public TimeUnitDialog(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
//        setDialogValues();
        addDialogFields();
        setHints();
    }

    private void setHints() {
        var nameHint = new TextPrompt(timeUnit.getName(), nameField, TextPrompt.Show.FOCUS_LOST);
        nameHint.changeAlpha(0.5f);
        nameHint.changeStyle(Font.ITALIC);
    }

    private void setDialogValues() {
        nameField.setText(timeUnit.getName());
        hourField.setText(String.valueOf(timeUnit.getHourCount()));
        minuteField.setText(String.valueOf(timeUnit.getMinuteCount()));
    }

    private void addDialogFields() {
        add("Name of the unit", nameField, true);
        add("Hours unit represents", hourField, true);
        add("Minutes unit represents", minuteField, true);
    }


    @Override
    public TimeUnit getEntity() {
        return timeUnit;
    }
}
