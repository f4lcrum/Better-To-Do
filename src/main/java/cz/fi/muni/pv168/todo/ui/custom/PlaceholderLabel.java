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
        int alpha = 128;
        Color foreground = Color.gray;
        int red = foreground.getRed();
        int green = foreground.getGreen();
        int blue = foreground.getBlue();
        Color color = new Color(red, green, blue, alpha);
        super.setForeground(color);
    }
}
