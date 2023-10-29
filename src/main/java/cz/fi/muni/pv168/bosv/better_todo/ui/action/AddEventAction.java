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

public final class AddEventAction extends AbstractAction {
    private final JTable todoTable;

    private final ListModel<Category> categoryListModel;
    private final ListModel<Status> statusListModel;

    public AddEventAction(JTable todoTable, ListModel<Category> categoryListModel, ListModel<Status> statusListModel) {
        super("Add", Icons.ADD_ICON);
        this.todoTable = todoTable;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
        putValue(SHORT_DESCRIPTION, "Adds new event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var todoTableModel = (TodoTableModel) todoTable.getModel();
        var dialog = new EventDialog(createPrefilledEvent(), categoryListModel, statusListModel);
        dialog.show(todoTable, "Add Event");
    }

    private Event createPrefilledEvent()
    {
        return new Event(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Fajnový event",
                Status.PLANNED,
                new Category(UUID.randomUUID(), "DnB Session", Color.BLACK),
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now(),
                "Kalba na Duchonke"
        );
    }
}
