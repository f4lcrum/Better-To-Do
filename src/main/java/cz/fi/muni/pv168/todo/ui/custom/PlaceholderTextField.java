package cz.fi.muni.pv168.todo.ui.custom;


import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private boolean shown = true;

    public PlaceholderTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
        addFocusListener(new PlaceholderFocusListener());
        setPlaceholder();
    }

    private void setPlaceholder() {
        shown = true;
        setText(placeholder);
        setForeground(Color.GRAY);
        setFont(getFont().deriveFont(Font.ITALIC));
    }

    private void removePlaceholder() {
        shown = false;
        setText("");
        setForeground(Color.BLACK);
        setFont(getFont().deriveFont(Font.PLAIN));
    }

    private class PlaceholderFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
            if (shown) {
                removePlaceholder();
            }
        }

        public void focusLost(FocusEvent e) {
            if (!shown && getText().isEmpty()) {
                setPlaceholder();
            }
        }
    }
}

