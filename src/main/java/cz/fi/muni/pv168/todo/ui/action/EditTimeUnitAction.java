package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.dialog.TimeUnitDialog;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitTableModel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditTimeUnitAction extends AbstractAction {

    private final JTable timeUnitTable;

    public EditTimeUnitAction(JTable timeUnitTable) {
        super("Edit time unit", Icons.EDIT_ICON);
        this.timeUnitTable = timeUnitTable;
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
        var employeeTableModel = (TimeUnitTableModel) timeUnitTable.getModel();
        int modelRow = timeUnitTable.convertRowIndexToModel(selectedRows[0]);
        var employee = employeeTableModel.getEntity(modelRow);
        var dialog = new TimeUnitDialog(employee);
        dialog.show(timeUnitTable, "Edit Event");
    }
}
