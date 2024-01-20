package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.todo.ui.main.MainWindowCategory;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class EditCategoryAction extends AbstractAction {

    private final JTable categoryTable;
    private final Validator<Category> categoryValidator;
    private final MainWindowCategory mainWindowCategory;
    private final Runnable refresh;


    public EditCategoryAction(JTable categoryTable, DependencyProvider dependencyProvider, MainWindowCategory mainWindowCategory, Runnable refresh) {
        super("Edit category", Icons.EDIT_ICON);
        this.categoryTable = categoryTable;
        this.categoryValidator = Objects.requireNonNull(dependencyProvider.getCategoryValidator());
        this.mainWindowCategory = mainWindowCategory;
        this.refresh = refresh;
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
        var categoryTableModel = mainWindowCategory.getTableModel();
        int modelRow = categoryTable.convertRowIndexToModel(selectedRows[0]);
        var category = categoryTableModel.getEntity(modelRow);
        var dialog = new CategoryDialog(category, true, categoryValidator);
        dialog.show(categoryTable, "Edit Category")
                .ifPresent(categoryTableModel::updateRow);
        refresh.run();
    }
}
