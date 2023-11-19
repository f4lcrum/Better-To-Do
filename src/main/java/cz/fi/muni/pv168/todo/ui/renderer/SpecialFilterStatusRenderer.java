package cz.fi.muni.pv168.todo.ui.renderer;

import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterStatusValues;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

public class SpecialFilterStatusRenderer extends AbstractRenderer<SpecialFilterStatusValues> {

    public SpecialFilterStatusRenderer() {
        super(SpecialFilterStatusValues.class);
    }

    protected void updateLabel(JLabel label, SpecialFilterStatusValues value) {
        if (Objects.requireNonNull(value) == SpecialFilterStatusValues.ALL) {
            renderAll(label);
        }
    }

    private static void renderAll(JLabel label) {
        label.setText("ALL");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }
}
