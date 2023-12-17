package cz.fi.muni.pv168.todo.ui.dialog;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

import cz.fi.muni.pv168.todo.ui.custom.PlaceholderLabel;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextArea;
import cz.fi.muni.pv168.todo.ui.custom.PlaceholderTextField;
import net.miginfocom.swing.MigLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.Optional;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();

    EntityDialog() {
        panel.setLayout(new MigLayout("wrap 2"));
    }

    void add(String labelText, String placeholder, PlaceholderTextField component) {
        component.setPlaceholder(new PlaceholderLabel(placeholder));
        addMandatory(labelText, component);
    }

    void addMandatory(String labelText, JComponent component) {
        var label = new JLabel(String.format("<html>%s<font color='red'>%s</font>: </html>", labelText, "*"));
        add(label, component);
    }

    void addOptional(String labelText, JComponent component) {
        var label = new JLabel(String.format("%s: ", labelText));
        add(label, component);
    }

    private void add(JLabel label, JComponent component) {
        panel.add(label);
        panel.add(component, "wmin 250lp, grow");
    }

    void addDescription(String labelText, String placeholder, PlaceholderTextArea component) {
        var label = new JLabel(String.format("%s: ", labelText));
        component.setLineWrap(true);
        component.setPlaceholder(new PlaceholderLabel(placeholder));
        JScrollPane pane = new JScrollPane(component);
        panel.add(label);
        panel.add(pane, "wmin 250lp, hmin 100lp, grow");
    }

    void addTime(String labelText, PlaceholderTextField hours, PlaceholderTextField minutes) {
        var label = new JLabel(String.format("<html>%s<font color='red'>%s</font>: </html>", labelText, "*"));
        hours.setPlaceholder(new PlaceholderLabel("8"));
        minutes.setPlaceholder(new PlaceholderLabel("30"));
        panel.add(label);
        panel.add(hours, "wmin 30, split 4");
        panel.add(new JLabel("h"));
        panel.add(minutes, "wmin 30");
        panel.add(new JLabel("m"));
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title, OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);

        return result == OK_OPTION ? Optional.of(getEntity()) : Optional.empty();
    }
}
