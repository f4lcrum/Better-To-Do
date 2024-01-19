package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.MainWindowCategory;
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
    private final MainWindowCategory mainWindowCategory;

    public AddCategoryAction(JTable categoryTable, MainWindowCategory mainWindowCategory) {
        super("Add category", Icons.ADD_ICON);
        this.categoryTable = categoryTable;
        this.categoryValidator = Objects.requireNonNull(mainWindowCategory.getCategoryValidator());
        this.mainWindowCategory = mainWindowCategory;
        putValue(SHORT_DESCRIPTION, "Adds new category");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var categoryTableModel = mainWindowCategory.getCategoryTableModel();
        var dialog = new CategoryDialog(createPrefilledCategory(), false, categoryValidator);
        dialog.show(categoryTable, "Add Category")
                .ifPresent(categoryTableModel::addRow);
        mainWindowCategory.refreshModel();
    }


    private Category createPrefilledCategory() {
        return new Category(
                UUID.randomUUID(),
                "Dinner with friends",
                Color.RED
        );
    }
}
