package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.EditCategoryAction;
import cz.fi.muni.pv168.todo.ui.main.MainWindowCategory;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class CategoryButtonTabStrategy implements ButtonTabStrategy {

    private final AddCategoryAction addAction;
    private final EditCategoryAction editAction;
    private final DeleteCategoryAction deleteAction;

    public CategoryButtonTabStrategy(JTable table, DependencyProvider dependencyProvider, MainWindowCategory mainWindowCategory, Runnable refresh) {
        this.addAction = new AddCategoryAction(table, dependencyProvider, mainWindowCategory);
        this.editAction = new EditCategoryAction(table, dependencyProvider, mainWindowCategory, refresh);
        this.deleteAction = new DeleteCategoryAction(table, mainWindowCategory);
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
