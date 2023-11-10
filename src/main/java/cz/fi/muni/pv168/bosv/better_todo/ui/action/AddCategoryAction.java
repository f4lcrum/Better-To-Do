package cz.fi.muni.pv168.bosv.better_todo.ui.action;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.dialog.CategoryDialog;
import cz.fi.muni.pv168.bosv.better_todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.time.LocalDate;

import java.time.LocalTime;
import java.util.UUID;

public final class AddCategoryAction extends AbstractAction {
    private final JTable categoryTable;


    public AddCategoryAction(JTable categoryTable) {
        super("Add", Icons.ADD_ICON);
        this.categoryTable = categoryTable;
        putValue(SHORT_DESCRIPTION, "Adds new category");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var todoTableModel = (TodoTableModel) categoryTable.getModel();
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
