package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.CreateTemplateFromEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteEventAction;
import cz.fi.muni.pv168.todo.ui.action.EditEventAction;
import cz.fi.muni.pv168.todo.ui.main.MainWindowEvent;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListModel;

public class EventButtonTabStrategy implements ButtonTabStrategy {
    private final AddEventAction addAction;
    private final EditEventAction editAction;
    private final DeleteEventAction deleteAction;

    public EventButtonTabStrategy(JTable table, ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel, ListModel<Template> templateListModel,
                                  MainWindowEvent mainWindowEvent, DependencyProvider dependencyProvider, CreateTemplateFromEventAction action, Runnable refresh) {
        this.addAction = new AddEventAction(table, categoryListModel, timeUnitListModel, templateListModel, mainWindowEvent, dependencyProvider, action, refresh);
        this.editAction = new EditEventAction(table, categoryListModel, timeUnitListModel, templateListModel, mainWindowEvent, dependencyProvider, action, refresh);
        this.deleteAction = new DeleteEventAction(table, mainWindowEvent, refresh);
    }

    @Override
    public AbstractAction getAddAction() {
        return addAction;
    }

    @Override
    public AbstractAction getEditAction() {
        return editAction;
    }

    @Override
    public AbstractAction getDeleteAction() {
        return deleteAction;
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
