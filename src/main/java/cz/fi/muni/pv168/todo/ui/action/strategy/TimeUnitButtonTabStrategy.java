package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.action.AddTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.EditTimeUnitAction;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class TimeUnitButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;

    public TimeUnitButtonTabStrategy(JTable table) {
        this.table = table;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddTimeUnitAction(table);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditTimeUnitAction(table);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteTimeUnitAction(table);
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
