package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;

import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterCategoryValues;

import javax.swing.*;
import java.awt.*;

public class SpecialTemplateRenderer extends AbstractRenderer<SpecialTemplateValues> {
    public SpecialTemplateRenderer() {
        super(SpecialTemplateValues.class);
    }

    protected void updateLabel(JLabel label, SpecialTemplateValues value) {
        switch (value) {
            case NONE -> renderNone(label);
        }
    }

    private static void renderNone(JLabel label) {
        label.setText("None");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setForeground(Color.GRAY);
    }
}
