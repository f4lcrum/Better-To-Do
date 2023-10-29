package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;

import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterStatusValues;

import javax.swing.*;
import java.awt.*;

public class SpecialFilterStatusRenderer extends AbstractRenderer<SpecialFilterStatusValues> {
    public SpecialFilterStatusRenderer() {
        super(SpecialFilterStatusValues.class);
    }

    protected void updateLabel(JLabel label, SpecialFilterStatusValues value) {
        switch (value) {
            case ALL -> renderAll(label);
        }
    }

    private static void renderAll(JLabel label) {
        label.setText("STATUS");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }
}
