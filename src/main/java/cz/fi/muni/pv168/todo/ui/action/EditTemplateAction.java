package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Status;
import cz.fi.muni.pv168.todo.ui.dialog.TemplateDialog;
import cz.fi.muni.pv168.todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditTemplateAction extends AbstractAction {

    private final JTable templateTable;

    private final ListModel<Category> categoryListModel;
    private final ListModel<Status> statusListModel;

    public EditTemplateAction(JTable templateTable, ListModel<Category> categoryListModel, ListModel<Status> statusListModel) {
        super("Edit template", Icons.EDIT_ICON);
        this.templateTable = templateTable;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
        putValue(SHORT_DESCRIPTION, "Edits selected template");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        putValue(Action.SMALL_ICON, Icons.EDIT_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = templateTable.getSelectedRows();

        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (templateTable.isEditing()) {
            templateTable.getCellEditor().cancelCellEditing();
        }

        var templateTableModel = (TemplateTableModel) templateTable.getModel();
        int modelRow = templateTable.convertRowIndexToModel(selectedRows[0]);
        var template = templateTableModel.getEntity(modelRow);
        var dialog = new TemplateDialog(template, categoryListModel, statusListModel);
        dialog.show(templateTable, "Edit Template");
    }
}
