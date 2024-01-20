package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.action.AddTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.EditTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.main.MainWindowTimeUnit;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class TimeUnitButtonTabStrategy implements ButtonTabStrategy {

    private final AddTimeUnitAction addAction;
    private final EditTimeUnitAction editAction;
    private final DeleteTimeUnitAction deleteAction;

    public TimeUnitButtonTabStrategy(JTable table, DependencyProvider dependencyProvider, MainWindowTimeUnit mainWindowTimeUnit, Runnable refresh) {
        this.addAction = new AddTimeUnitAction(table, dependencyProvider, mainWindowTimeUnit, refresh);
        this.editAction = new EditTimeUnitAction(table, dependencyProvider, mainWindowTimeUnit, refresh);
        this.deleteAction = new DeleteTimeUnitAction(table, mainWindowTimeUnit, refresh);
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
