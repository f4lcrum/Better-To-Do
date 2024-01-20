package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.async.DeleteActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTimeUnit;
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
    private final MainWindowTimeUnit mainWindowTimeUnit;
    private final Runnable refresh;

    public DeleteTimeUnitAction(JTable timeUnitTable, MainWindowTimeUnit mainWindowTimeUnit, Runnable refresh) {
        super("Delete time unit", Icons.DELETE_ICON);
        this.timeUnitTable = timeUnitTable;
        this.mainWindowTimeUnit = mainWindowTimeUnit;
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Deletes selected time unit");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        putValue(Action.SMALL_ICON, Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var timeUnitTableModel = mainWindowTimeUnit.getTableModel();
        var stream = Arrays.stream(timeUnitTable.getSelectedRows())
                .map(timeUnitTable::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder());
        new DeleteActionSwingWorker<>(timeUnitTableModel, refresh, stream).execute();
    }
}
