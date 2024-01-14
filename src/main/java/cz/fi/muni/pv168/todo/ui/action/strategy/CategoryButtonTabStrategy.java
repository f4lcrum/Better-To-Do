package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.EditCategoryAction;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class CategoryButtonTabStrategy implements ButtonTabStrategy {

    private final AddCategoryAction addAction;
    private final EditCategoryAction editAction;
    private final DeleteCategoryAction deleteAction;

    public CategoryButtonTabStrategy(JTable table, MainWindow mainWindow) {
        this.addAction = new AddCategoryAction(table, mainWindow);
        this.editAction = new EditCategoryAction(table, mainWindow);
        this.deleteAction = new DeleteCategoryAction(table, mainWindow);
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
