package cz.fi.muni.pv168.bosv.better_todo.ui.action;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
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

    private final ListModel<Category> categoryListModel;
    private final ListModel<Status> statusListModel;

    public AddCategoryAction(JTable categoryTable, ListModel<Category> categoryListModel, ListModel<Status> statusListModel) {
        super("Add category", Icons.ADD_ICON);
        this.categoryTable = categoryTable;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
        putValue(SHORT_DESCRIPTION, "Adds new category");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var todoTableModel = (TodoTableModel) categoryTable.getModel();
        var dialog = new EventDialog(createPrefilledEvent(), categoryListModel, statusListModel);
        dialog.show(categoryTable, "Add Category");
    }

    private Event createPrefilledEvent()
    {
        return new Event(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Dinner",
                Status.PLANNED,
                new Category(UUID.randomUUID(), "Social", Color.BLACK),
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now(),
                "Dinner with friends"
        );
    }
}
