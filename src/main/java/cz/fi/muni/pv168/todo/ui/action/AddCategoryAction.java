package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.CategoryColor;
import cz.fi.muni.pv168.todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class AddCategoryAction extends AbstractAction {

    private final JTable categoryTable;

    private final ListModel<Color> categoryColorListModel;


    public AddCategoryAction(JTable categoryTable, ListModel<Color> categoryColorListModel) {
        super("Add category", Icons.ADD_ICON);
        this.categoryColorListModel = categoryColorListModel;
        this.categoryTable = categoryTable;
        putValue(SHORT_DESCRIPTION, "Adds new category");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new CategoryDialog(createPrefilledCategory(), categoryColorListModel);
        dialog.show(categoryTable, "Add Category");
    }


    private Category createPrefilledCategory() {
        return new Category(
                UUID.randomUUID(),
                "Dinner with friends",
                Color.RED
        );
    }
}
