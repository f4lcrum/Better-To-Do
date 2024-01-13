package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteEventAction;
import cz.fi.muni.pv168.todo.ui.action.EditEventAction;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateListModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitListModel;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class EventButtonTabStrategy implements ButtonTabStrategy {

    private final AddEventAction addAction;
    private final EditEventAction editAction;
    private final DeleteEventAction deleteAction;

    public EventButtonTabStrategy(JTable table, CategoryListModel categoryListModel, TimeUnitListModel timeUnitListModel,
                                  TemplateListModel templateListModel, MainWindow mainWindow) {
        this.addAction = new AddEventAction(table, categoryListModel, timeUnitListModel, templateListModel, mainWindow);
        this.editAction = new EditEventAction(table, categoryListModel, timeUnitListModel, templateListModel, mainWindow);
        this.deleteAction = new DeleteEventAction(table, mainWindow);
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
