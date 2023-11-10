package cz.fi.muni.pv168.bosv.better_todo.ui.action;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.bosv.better_todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.CategoryTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditCategoryAction extends AbstractAction {

    private final JTable categoryTable;


    public EditCategoryAction(JTable categoryTable) {
        super("Edit", Icons.EDIT_ICON);
        this.categoryTable = categoryTable;
        putValue(SHORT_DESCRIPTION, "Edits selected category");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = categoryTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (categoryTable.isEditing()) {
            categoryTable.getCellEditor().cancelCellEditing();
        }
        var categoryTableModel = (CategoryTableModel) categoryTable.getModel();
        int modelRow = categoryTable.convertRowIndexToModel(selectedRows[0]);
        var category = categoryTableModel.getEntity(modelRow);
        var dialog = new CategoryDialog(category);
        dialog.show(categoryTable, "Edit Category");
    }
}
