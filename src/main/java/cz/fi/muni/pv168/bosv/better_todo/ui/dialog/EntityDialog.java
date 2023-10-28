package cz.fi.muni.pv168.bosv.better_todo.ui.dialog;

import static javax.swing.JOptionPane.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Optional;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();

    EntityDialog() {
        panel.setLayout(new MigLayout("wrap 2"));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);
        panel.add(label);
        panel.add(component, "wmin 250lp, grow");
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);

        return result == OK_OPTION ? Optional.of(getEntity()) : Optional.empty();
    }
}
