package cz.fi.muni.pv168.todo.ui.renderer;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

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
