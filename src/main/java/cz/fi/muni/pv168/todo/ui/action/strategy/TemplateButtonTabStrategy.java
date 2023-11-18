package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.action.AddTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.EditTemplateAction;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class TemplateButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;
    private final CategoryListModel categoryListModel;
    private final StatusListModel statusListModel;

    public TemplateButtonTabStrategy(JTable table, CategoryListModel categoryListModel, StatusListModel statusListModel) {
        this.table = table;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddTemplateAction(table, categoryListModel, statusListModel);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditTemplateAction(table, categoryListModel, statusListModel);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteTemplateAction(table);
    }

    @Override
    public Boolean statusFilterEnabled() {
        return false;
    }

    @Override
    public Boolean durationFilterEnabled() {
        return false;
    }

    @Override
    public Boolean categoryFilterEnabled() {
        return false;
    }
}
