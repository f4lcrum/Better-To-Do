package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.AddTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.EditTemplateAction;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTemplate;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListModel;

public class TemplateButtonTabStrategy implements ButtonTabStrategy {

    private final AddTemplateAction addAction;
    private final EditTemplateAction editAction;
    private final DeleteTemplateAction deleteAction;

    public TemplateButtonTabStrategy(JTable table, DependencyProvider dependencyProvider, ListModel<Category> categoryListModel,
                                     ListModel<TimeUnit> timeUnitListModel, MainWindowTemplate mainWindowTemplate, Runnable refresh) {
        this.addAction = new AddTemplateAction(table, mainWindowTemplate, dependencyProvider, categoryListModel, timeUnitListModel, refresh);
        this.editAction = new EditTemplateAction(table, dependencyProvider, mainWindowTemplate, categoryListModel, timeUnitListModel, refresh);
        this.deleteAction = new DeleteTemplateAction(table, mainWindowTemplate, refresh);
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
