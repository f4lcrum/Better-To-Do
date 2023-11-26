package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.Status;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public final class AddEventAction extends AbstractAction {

    private final JTable todoTable;

    private final ListModel<Category> categoryListModel;

    public AddEventAction(JTable todoTable, ListModel<Category> categoryListModel) {
        super("Add event", Icons.ADD_ICON);
        this.todoTable = todoTable;
        this.categoryListModel = categoryListModel;
        putValue(SHORT_DESCRIPTION, "Adds new event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new EventDialog(createPrefilledEvent(), categoryListModel);
        dialog.show(todoTable, "Add Event");
    }

    private Event createPrefilledEvent() {
        return new Event(
                UUID.randomUUID(),
                "Dinner",
                Status.PLANNED,
                new Category(UUID.randomUUID(), "Social", Color.BLACK),
                LocalDate.now(),
                LocalTime.now(),
                LocalTime.now(),
                "Dinner with parents"
        );
    }
}
