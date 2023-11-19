package cz.fi.muni.pv168.todo.ui.renderer;

import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterCategoryValues;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

public class SpecialFilterCategoryRenderer extends AbstractRenderer<SpecialFilterCategoryValues> {

    public SpecialFilterCategoryRenderer() {
        super(SpecialFilterCategoryValues.class);
    }

    protected void updateLabel(JLabel label, SpecialFilterCategoryValues value) {
        if (Objects.requireNonNull(value) == SpecialFilterCategoryValues.ALL) {
            renderAll(label);
        }
    }

    private static void renderAll(JLabel label) {
        label.setText("ALL");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }

}
