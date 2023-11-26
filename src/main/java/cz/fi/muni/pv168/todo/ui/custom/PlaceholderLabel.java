package cz.fi.muni.pv168.todo.ui.custom;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;

public class PlaceholderLabel extends JLabel {
    public PlaceholderLabel(String text) {
        super(text);
        setStyle();
    }

    private void setStyle() {
        setFont(getFont().deriveFont(Font.ITALIC));
        super.setForeground(Color.gray);
    }
}
