package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.AddTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.EditEventAction;
import cz.fi.muni.pv168.todo.ui.action.EditTimeUnitAction;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class TimeUnitButtonTabStrategy implements ButtonTabStrategy {

    private final AddTimeUnitAction addAction;
    private final EditTimeUnitAction editAction;
    private final DeleteTimeUnitAction deleteAction;

    public TimeUnitButtonTabStrategy(JTable table, MainWindow mainWindow) {
        this.addAction = new AddTimeUnitAction(table, mainWindow);
        this.editAction = new EditTimeUnitAction(table, mainWindow);
        this.deleteAction = new DeleteTimeUnitAction(table, mainWindow);
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
