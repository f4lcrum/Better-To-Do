package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.EditCategoryAction;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class CategoryButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;

    public CategoryButtonTabStrategy(JTable table) {
        this.table = table;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddCategoryAction(table);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditCategoryAction(table);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteCategoryAction(table);
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
