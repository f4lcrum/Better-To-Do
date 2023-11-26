package cz.fi.muni.pv168.todo.ui.custom;

import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField {

    private PlaceholderLabel placeholder;

    public PlaceholderTextField() {
        super();
        setLayout(new BorderLayout());
        addFocusListener(new PlaceholderFocusListener());
    }

    public void setPlaceholder(PlaceholderLabel label) {
        placeholder = label;
    }

    private class PlaceholderFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
            placeholder.setVisible(false);
        }

        public void focusLost(FocusEvent e) {
            placeholder.setVisible(getText().isEmpty());
        }
    }
}