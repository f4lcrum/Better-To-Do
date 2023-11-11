package cz.fi.muni.pv168.bosv.better_todo.ui.dialog;

import static javax.swing.JOptionPane.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();

    EntityDialog() {
        panel.setLayout(new MigLayout("wrap 2"));
    }

    void add(String labelText, JComponent component, boolean isMandatory) {

        var label = new JLabel(labelText);
        if (isMandatory) {
            label.setText(String.format("<html>%s<font color='red'>%s</font>: </html>", label.getText(), "*"));
            panel.add(label);
        } else {
            panel.add(label);
        }
        panel.add(component, "wmin 250lp, grow");
    }

    void addDescritpion(String labelText, JComponent component) {
        var label = new JLabel(String.format("%s: ", labelText));
        panel.add(label);
        panel.add(component, "wmin 250lp, hmin 100lp, grow");

    void addTime(String labelText, JComponent component1, JComponent component2) {
        var label = new JLabel(labelText);
        var hours = new JLabel("h");
        var minutes = new JLabel("m");
        panel.add(label);
        panel.add(component1, "wmin 30, split 4");
        panel.add(hours);
        panel.add(component2, "wmin 30");
        panel.add(minutes);
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);

        return result == OK_OPTION ? Optional.of(getEntity()) : Optional.empty();
    }
}
