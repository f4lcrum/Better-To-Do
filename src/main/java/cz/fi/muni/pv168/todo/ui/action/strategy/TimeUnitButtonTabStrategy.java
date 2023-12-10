package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.AddTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTimeUnitAction;
import cz.fi.muni.pv168.todo.ui.action.EditTimeUnitAction;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class TimeUnitButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;
    private final MainWindow mainWindow;

    public TimeUnitButtonTabStrategy(JTable table, MainWindow mainWindow) {
        this.table = table;
        this.mainWindow = mainWindow;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddTimeUnitAction(table, mainWindow);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditTimeUnitAction(table, mainWindow);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteTimeUnitAction(table, mainWindow);
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
