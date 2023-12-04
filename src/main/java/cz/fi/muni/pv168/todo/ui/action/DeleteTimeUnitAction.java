package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteTimeUnitAction extends AbstractAction {

    private final JTable timeUnitTable;

    public DeleteTimeUnitAction(JTable timeUnitTable) {
        super("Delete time unit", Icons.DELETE_ICON);
        this.timeUnitTable = timeUnitTable;
        putValue(SHORT_DESCRIPTION, "Deletes selected time unit");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        putValue(Action.SMALL_ICON, Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Arrays.stream(timeUnitTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(timeUnitTable::convertRowIndexToModel)
                .boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder());
    }

}
