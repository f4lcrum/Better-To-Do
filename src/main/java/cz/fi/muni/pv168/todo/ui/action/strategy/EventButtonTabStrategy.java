package cz.fi.muni.pv168.todo.ui.action.strategy;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.CategoryValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TemplateValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TimeUnitValidator;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.action.AddEventAction;
import cz.fi.muni.pv168.todo.ui.action.DeleteEventAction;
import cz.fi.muni.pv168.todo.ui.action.EditEventAction;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.TemplateListModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitListModel;

import java.sql.Time;
import javax.swing.AbstractAction;
import javax.swing.JTable;

public class EventButtonTabStrategy implements ButtonTabStrategy {

    private final JTable table;
    private final CategoryListModel categoryListModel;
    private final TimeUnitListModel timeUnitListModel;
    private final TemplateListModel templateListModel;

    private final MainWindow mainWindow;

    public EventButtonTabStrategy(JTable table, CategoryListModel categoryListModel, TimeUnitListModel timeUnitListModel,
                                  TemplateListModel templateListModel, MainWindow mainWindow) {
        this.table = table;
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateListModel = templateListModel;
        this.mainWindow = mainWindow;
    }

    @Override
    public AbstractAction getAddAction() {
        return new AddEventAction(table, categoryListModel, timeUnitListModel, templateListModel, mainWindow);
    }

    @Override
    public AbstractAction getEditAction() {
        return new EditEventAction(table, categoryListModel, timeUnitListModel, templateListModel, mainWindow);
    }

    @Override
    public AbstractAction getDeleteAction() {
        return new DeleteEventAction(table, mainWindow);
    }

    @Override
    public Boolean statusFilterEnabled() {
        return true;
    }

    @Override
    public Boolean durationFilterEnabled() {
        return true;
    }

    @Override
    public Boolean categoryFilterEnabled() {
        return true;
    }
}
