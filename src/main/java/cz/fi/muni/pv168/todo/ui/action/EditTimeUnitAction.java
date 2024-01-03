package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.TimeUnitDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditTimeUnitAction extends AbstractAction {

    private final JTable timeUnitTable;
    private final MainWindow mainWindow;

    public EditTimeUnitAction(JTable timeUnitTable, MainWindow mainWindow) {
        super("Edit time unit", Icons.EDIT_ICON);
        this.timeUnitTable = timeUnitTable;
        this.mainWindow = mainWindow;
        putValue(SHORT_DESCRIPTION, "Edits selected event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        putValue(Action.SMALL_ICON, Icons.EDIT_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = timeUnitTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (timeUnitTable.isEditing()) {
            timeUnitTable.getCellEditor().cancelCellEditing();
        }
        var timeUnitTableModel = mainWindow.getTimeUnitTableModel();
        int modelRow = timeUnitTable.convertRowIndexToModel(selectedRows[0]);
        var timeUnit = timeUnitTableModel.getEntity(modelRow);
        if (timeUnit.isDefault()) {
            return;
        }
        var dialog = new TimeUnitDialog(timeUnit, true);
        dialog.show(timeUnitTable, "Edit time unit")
                .ifPresent(timeUnitTableModel::updateRow);
        mainWindow.refreshTimeUnitListModel();
    }
}
