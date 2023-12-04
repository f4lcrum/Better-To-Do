package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.dialog.TimeUnitDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class AddTimeUnitAction extends AbstractAction {

    private final JTable timeUnitTable;

    public AddTimeUnitAction(JTable timeUnitTable) {
        super("Add duration unit", Icons.ADD_ICON);
        this.timeUnitTable = timeUnitTable;
        putValue(SHORT_DESCRIPTION, "Adds new duration unit");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new TimeUnitDialog(createPrefilledTimeUnit());
        dialog.show(timeUnitTable, "Add Duration Unit");
    }

    private TimeUnit createPrefilledTimeUnit() {
        return new TimeUnit(
                UUID.randomUUID(),
                "Sprint",
                14 * 24, // 14 days,
                0
        );
    }
}
