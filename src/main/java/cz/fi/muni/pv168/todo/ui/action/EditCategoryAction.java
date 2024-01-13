package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class EditCategoryAction extends AbstractAction {

    private final JTable categoryTable;
    private final Validator<Category> categoryValidator;
    private final MainWindow mainWindow;

    public EditCategoryAction(JTable categoryTable, MainWindow mainWindow) {
        super("Edit category", Icons.EDIT_ICON);
        this.categoryTable = categoryTable;
        this.categoryValidator = Objects.requireNonNull(mainWindow.getCategoryValidator());
        this.mainWindow = mainWindow;
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
        var categoryTableModel = mainWindow.getCategoryTableModel();
        int modelRow = categoryTable.convertRowIndexToModel(selectedRows[0]);
        var category = categoryTableModel.getEntity(modelRow);
        var dialog = new CategoryDialog(category, true, categoryValidator);
        dialog.show(categoryTable, "Edit Category")
                .ifPresent(categoryTableModel::updateRow);
        mainWindow.refreshCategoryListModel();
        mainWindow.refreshEventModel();
    }
}
