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

    private final AddTemplateAction addAction;
    private final EditTemplateAction editAction;
    private final DeleteTemplateAction deleteAction;

    public TemplateButtonTabStrategy(JTable table, CategoryListModel categoryListModel,
                                     TimeUnitListModel timeUnitListModel, MainWindow mainWindow) {
        this.addAction = new AddTemplateAction(table, mainWindow, categoryListModel, timeUnitListModel);
        this.editAction = new EditTemplateAction(table, mainWindow, categoryListModel, timeUnitListModel);
        this.deleteAction = new DeleteTemplateAction(table, mainWindow);
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
