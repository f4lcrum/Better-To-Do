package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.action.AddCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteCategoryAction;
import cz.fi.muni.pv168.todo.ui.action.EditCategoryAction;
import cz.fi.muni.pv168.todo.ui.model.CategoryColorListModel;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class CategoryButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;
    private final CategoryColorListModel categoryColorListModel;

    public CategoryButtonTabStrategy(JTable table, CategoryColorListModel categoryColorListModel) {
        this.table = table;
        this.categoryColorListModel = categoryColorListModel;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddCategoryAction(table, categoryColorListModel);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditCategoryAction(table, categoryColorListModel);
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
