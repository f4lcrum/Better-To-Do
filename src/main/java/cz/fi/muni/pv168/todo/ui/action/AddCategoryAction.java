package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.UUID;

public final class AddCategoryAction extends AbstractAction {

    private final JTable categoryTable;
    private final Validator<Category> categoryValidator;
    private final MainWindow mainWindow;

    public AddCategoryAction(JTable categoryTable, MainWindow mainWindow) {
        super("Add category", Icons.ADD_ICON);
        this.categoryTable = categoryTable;
        this.categoryValidator = Objects.requireNonNull(mainWindow.getCategoryValidator());
        this.mainWindow = mainWindow;
        putValue(SHORT_DESCRIPTION, "Adds new category");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var categoryTableModel = mainWindow.getCategoryTableModel();
        var dialog = new CategoryDialog(createPrefilledCategory(), false, categoryValidator);
        dialog.show(categoryTable, "Add Category")
                .ifPresent(categoryTableModel::addRow);
        mainWindow.refreshCategoryListModel();
    }


    private Category createPrefilledCategory() {
        return new Category(
                UUID.randomUUID(),
                "Dinner with friends",
                Color.RED
        );
    }
}
