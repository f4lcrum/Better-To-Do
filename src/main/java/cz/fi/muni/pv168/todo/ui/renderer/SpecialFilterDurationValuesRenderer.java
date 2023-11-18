package cz.fi.muni.pv168.todo.ui.renderer;

import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterDurationValues;

import javax.swing.*;
import java.awt.*;

public class SpecialFilterDurationValuesRenderer extends AbstractRenderer<SpecialFilterDurationValues> {
    public SpecialFilterDurationValuesRenderer() {
        super(SpecialFilterDurationValues.class);
    }

    protected void updateLabel(JLabel label, SpecialFilterDurationValues value) {
        switch (value) {
            case ALL -> renderAll(label);
        }
    }

    private static void renderAll(JLabel label) {
        label.setText("ALL");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }
}

