package cz.fi.muni.pv168.bosv.better_todo.ui.action;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditEventAction extends AbstractAction {

    private final JTable todoTable;

    private final ListModel<Category> categoryListModel;
    private final ListModel<Status> statusListModel;

    public EditEventAction(JTable todoTable, ListModel<Category> categoryListModel, ListModel<Status> statusListModel) {
        super("Edit event", Icons.EDIT_ICON);
        this.todoTable = todoTable;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
        putValue(SHORT_DESCRIPTION, "Edits selected event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        putValue(Action.SMALL_ICON, Icons.EDIT_ICON);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = todoTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (todoTable.isEditing()) {
            todoTable.getCellEditor().cancelCellEditing();
        }
        var employeeTableModel = (TodoTableModel) todoTable.getModel();
        int modelRow = todoTable.convertRowIndexToModel(selectedRows[0]);
        var employee = employeeTableModel.getEntity(modelRow);
        var dialog = new EventDialog(employee, categoryListModel);
        dialog.show(todoTable, "Edit Event");
    }
}
