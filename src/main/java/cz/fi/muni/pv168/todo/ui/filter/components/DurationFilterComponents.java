package cz.fi.muni.pv168.todo.ui.filter.components;

import javax.swing.JPanel;
import javax.swing.JTextField;


public final class DurationFilterComponents {
    public final JPanel panel;
    public final JTextField minDurationField;
    public final JTextField maxDurationField;

    public DurationFilterComponents(JPanel panel, JTextField minDurationField, JTextField maxDurationField) {
        this.panel = panel;
        this.minDurationField = minDurationField;
        this.maxDurationField = maxDurationField;
    }
}
