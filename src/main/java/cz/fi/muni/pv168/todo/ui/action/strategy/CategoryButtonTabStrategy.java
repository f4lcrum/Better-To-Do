package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.Main;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.EditCategoryAction;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class CategoryButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;
    private MainWindow mainWindow;
    public CategoryButtonTabStrategy(JTable table, MainWindow mainWindow) {
        this.table = table;
        this.mainWindow = mainWindow;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddCategoryAction(table, mainWindow);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditCategoryAction(table, mainWindow);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteCategoryAction(table, mainWindow);
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
