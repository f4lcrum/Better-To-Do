package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.AddTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteTemplateAction;
import cz.fi.muni.pv168.todo.ui.action.EditTemplateAction;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitListModel;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class TemplateButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;
    private final CategoryListModel categoryListModel;
    private final TimeUnitListModel timeUnitListModel;
    private final MainWindow mainWindow;

    public TemplateButtonTabStrategy(JTable table, CategoryListModel categoryListModel,
                                     TimeUnitListModel timeUnitListModel, MainWindow mainWindow) {
        this.table = table;
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.mainWindow = mainWindow;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddTemplateAction(table, mainWindow, categoryListModel, timeUnitListModel);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditTemplateAction(table, mainWindow, categoryListModel, timeUnitListModel);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteTemplateAction(table, mainWindow);
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
