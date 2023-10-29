package cz.fi.muni.pv168.bosv.better_todo.ui.action;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.entity.Template;
import cz.fi.muni.pv168.bosv.better_todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.bosv.better_todo.ui.dialog.TemplateDialog;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public final class AddTemplateAction extends AbstractAction {
    private final JTable templateTable;

    private final ListModel<Category> categoryListModel;
    private final ListModel<Status> statusListModel;

    public AddTemplateAction(JTable templateTable, ListModel<Category> categoryListModel, ListModel<Status> statusListModel) {
        super("Add", Icons.ADD_ICON);
        this.templateTable = templateTable;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
        putValue(SHORT_DESCRIPTION, "Adds new template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var templateTableModel = (TemplateTableModel) templateTable.getModel();
        var dialog = new TemplateDialog(createPrefilledTemplate(), categoryListModel, statusListModel);
        dialog.show(templateTable, "Add Template");
    }

    private Template createPrefilledTemplate()
    {
        return new Template(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Fajnový event",
                "Fakt dost hustej even",
                new Category(UUID.randomUUID(), "DnB Session", Color.BLACK),
                LocalTime.now(),
                LocalTime.now()
        );
    }
}
