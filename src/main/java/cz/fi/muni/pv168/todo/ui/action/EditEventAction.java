package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditEventAction extends AbstractAction {

    private final JTable todoTable;
    private final ListModel<Category> categoryListModel;
    private final ListModel<TimeUnit> timeUnitListModel;
    private final ListModel<Template> templateListModel;
    private final MainWindow mainWindow;

    public EditEventAction(JTable todoTable, ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel,
                           ListModel<Template> templateListModel, MainWindow mainWindow) {
        super("Edit event", Icons.EDIT_ICON);
        this.todoTable = todoTable;
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateListModel = templateListModel;
        this.mainWindow = mainWindow;
        putValue(SHORT_DESCRIPTION, "Edits selected event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        putValue(Action.SMALL_ICON, Icons.EDIT_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = todoTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (todoTable.isEditing()) {
            todoTable.getCellEditor().cancelCellEditing();
        }
        var eventTableModel = mainWindow.getEventTableModel();
        int modelRow = todoTable.convertRowIndexToModel(selectedRows[0]);
        var employee = eventTableModel.getEntity(modelRow);
        var dialog = new EventDialog(employee, categoryListModel, timeUnitListModel, templateListModel, true);
        dialog.show(todoTable, "Edit Event")
                .ifPresent(eventTableModel::updateRow);
        mainWindow.refreshEventListModel();
    }
}
