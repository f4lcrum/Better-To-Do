package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;

import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterStatusValues;

import javax.swing.*;
import java.awt.*;

public class SpecialFilterCategoryRenderer extends AbstractRenderer<SpecialFilterCategoryValues> {
    public SpecialFilterCategoryRenderer() {
        super(SpecialFilterCategoryValues.class);
    }

    protected void updateLabel(JLabel label, SpecialFilterCategoryValues value) {
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
