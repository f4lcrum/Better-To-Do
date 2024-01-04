package cz.fi.muni.pv168.todo.ui.custom;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextArea extends JTextArea {

    private PlaceholderLabel placeholder;

    public PlaceholderTextArea() {
        super();
        setLayout(new BorderLayout());
        addFocusListener(new PlaceholderFocusListener());
        getDocument().addDocumentListener(new PlaceholderDocumentListnener());
    }

    public void setPlaceholder(PlaceholderLabel label) {
        placeholder = label;
        add(label);
        placeholder.setVisible(getText().isEmpty());
    }

    private void checkVisibility() {
        if (placeholder != null) {
            placeholder.setVisible(!hasFocus() && getText().isEmpty());
        }
    }

    private class PlaceholderFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
            checkVisibility();
        }

        public void focusLost(FocusEvent e) {
            checkVisibility();
        }
    }

    private class PlaceholderDocumentListnener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            checkVisibility();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            checkVisibility();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            checkVisibility();
        }
    }
}
