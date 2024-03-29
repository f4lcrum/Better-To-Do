package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.async.DeleteActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.main.MainWindowEvent;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public class DeleteEventAction extends AbstractAction {

    private final JTable eventTable;
    private final MainWindowEvent mainWindowEvent;
    private final Runnable refresh;

    public DeleteEventAction(JTable eventTable, MainWindowEvent mainWindowEvent, Runnable refresh) {
        super("Delete event", Icons.DELETE_ICON);
        this.eventTable = eventTable;
        this.mainWindowEvent = mainWindowEvent;
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Deletes selected event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
        putValue(Action.SMALL_ICON, Icons.DELETE_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var eventTableModel = mainWindowEvent.getTableModel();
        var stream = Arrays.stream(eventTable.getSelectedRows())
                .map(eventTable::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder());
        new DeleteActionSwingWorker<>(eventTableModel, refresh, stream).execute();
    }
}
