package cz.fi.muni.pv168.todo.ui.renderer;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;

import javax.swing.JLabel;

public class TimeUnitRenderer extends AbstractRenderer<TimeUnit> {

    public TimeUnitRenderer() {
        super(TimeUnit.class);
    }

    @Override
    protected void updateLabel(JLabel label, TimeUnit value) {
        if (value == null) {
            label.setText("");
            return;
        }
        label.setText(value.getName());
    }
}
