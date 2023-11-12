package cz.fi.muni.pv168.todo.ui.action;


import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.model.EventTableModel;
import cz.fi.muni.pv168.todo.ui.renderer.SpecialTemplateValues;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.util.Either;

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
    private final ListModel<Either<Template, SpecialTemplateValues>> templateList;

    public EditEventAction(JTable todoTable, ListModel<Category> categoryListModel, ListModel<Either<Template, SpecialTemplateValues>> templateList) {
        super("Edit event", Icons.EDIT_ICON);
        this.todoTable = todoTable;
        this.categoryListModel = categoryListModel;
        this.templateList = templateList;
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
        var employeeTableModel = (EventTableModel) todoTable.getModel();
        int modelRow = todoTable.convertRowIndexToModel(selectedRows[0]);
        var employee = employeeTableModel.getEntity(modelRow);
        var dialog = new EventDialog(employee, categoryListModel, templateList);
        dialog.show(todoTable, "Edit Event");
    }
}
