package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.main.MainWindowCategory;
import cz.fi.muni.pv168.todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

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

    public AddCategoryAction(JTable categoryTable, DependencyProvider dependencyProvider, MainWindowCategory mainWindowCategory) {
        super("Add category", Icons.ADD_ICON);
        this.categoryTable = categoryTable;
        this.categoryValidator = Objects.requireNonNull(dependencyProvider.getCategoryValidator());
        this.mainWindowCategory = mainWindowCategory;
        putValue(SHORT_DESCRIPTION, "Adds new category");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var categoryTableModel = mainWindowCategory.getTableModel();
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
