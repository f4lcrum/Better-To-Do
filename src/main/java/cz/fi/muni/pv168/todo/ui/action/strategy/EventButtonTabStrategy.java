package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteEventAction;
import cz.fi.muni.pv168.todo.ui.action.EditEventAction;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateListModel;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class EventButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;
    private final CategoryListModel categoryListModel;
    private final StatusListModel statusListModel;
    private final TemplateListModel templateListModel;

    public EventButtonTabStrategy(JTable table, CategoryListModel categoryListModel, StatusListModel statusListModel, TemplateListModel templateListModel) {
        this.table = table;
        this.categoryListModel = categoryListModel;
        this.statusListModel = statusListModel;
        this.templateListModel = templateListModel;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddEventAction(table, categoryListModel, templateListModel);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditEventAction(table, categoryListModel, templateListModel);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteEventAction(table);
    }

    @Override
    public Boolean statusFilterEnabled() {
        return true;
    }

    @Override
    public Boolean durationFilterEnabled() {
        return true;
    }

    @Override
    public Boolean categoryFilterEnabled() {
        return true;
    }
}
