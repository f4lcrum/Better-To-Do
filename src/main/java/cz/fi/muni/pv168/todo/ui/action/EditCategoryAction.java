package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.entity.CategoryColor;
import cz.fi.muni.pv168.todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.todo.ui.model.CategoryTableModel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class EditCategoryAction extends AbstractAction {

    private final JTable categoryTable;
    private final ListModel<Color> categoryColorListModel;


    public EditCategoryAction(JTable categoryTable, ListModel<Color> categoryColorListModel) {
        super("Edit category", Icons.EDIT_ICON);
        this.categoryTable = categoryTable;
        this.categoryColorListModel = categoryColorListModel;
        putValue(SHORT_DESCRIPTION, "Edits selected category");
        putValue(Action.SMALL_ICON, Icons.EDIT_ICON);
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
        var dialog = new CategoryDialog(category, categoryColorListModel);
        dialog.show(categoryTable, "Edit Category");
    }
}
