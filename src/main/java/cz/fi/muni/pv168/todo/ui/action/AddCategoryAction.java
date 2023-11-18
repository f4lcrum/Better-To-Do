package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.util.UUID;

public final class AddCategoryAction extends AbstractAction {
    private final JTable categoryTable;


    public AddCategoryAction(JTable categoryTable) {
        super("Add category", Icons.ADD_ICON);
        this.categoryTable = categoryTable;
        putValue(SHORT_DESCRIPTION, "Adds new category");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var categoryTableModel = (TodoTableModel) categoryTable.getModel();
        var dialog = new CategoryDialog(createPrefilledCategory());
        dialog.show(categoryTable, "Add Category");
    }


    private Category createPrefilledCategory()
    {
    return new Category(
            UUID.randomUUID(),
            "Dinner with friends",
            Color.RED
    );
    }
}
