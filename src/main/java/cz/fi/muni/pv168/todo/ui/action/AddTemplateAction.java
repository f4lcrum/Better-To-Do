package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Status;
import cz.fi.muni.pv168.todo.entity.Template;
import cz.fi.muni.pv168.todo.ui.dialog.TemplateDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.util.UUID;

public final class AddTemplateAction extends AbstractAction {

    private final JTable templateTable;

    private final ListModel<Category> categoryListModel;
    private final ListModel<Status> statusListModel;

    public AddTemplateAction(JTable templateTable, ListModel<Category> categoryListModel, ListModel<Status> statusListModel) {
        super("Add template", Icons.ADD_ICON);
        this.templateTable = templateTable;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
        putValue(SHORT_DESCRIPTION, "Adds new template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new TemplateDialog(createPrefilledTemplate(), categoryListModel, statusListModel);
        dialog.show(templateTable, "Add Template");
    }

    private Template createPrefilledTemplate() {
        return new Template(
                UUID.randomUUID(),
                "Restaurant",
                "",
                new Category(UUID.randomUUID(), "Social", Color.BLACK),
                LocalTime.now(),
                LocalTime.now()
        );
    }
}
