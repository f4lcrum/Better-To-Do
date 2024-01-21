package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TemplateCrudService;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.async.AddActionSwingWorker;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.main.MainWindowEvent;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public final class AddEventAction extends AbstractAction {

    private final JTable eventTable;
    private final ListModel<Category> categoryListModel;
    private final ListModel<TimeUnit> timeUnitListModel;
    private final ListModel<Template> templateListModel;
    private final Validator<Event> eventValidator;
    private final MainWindowEvent mainWindowEvent;
    private final CategoryCrudService categoryCrudService;
    private final CreateTemplateFromEventAction action;
    private final Runnable refresh;

    public AddEventAction(JTable eventTable, ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel, ListModel<Template> templateListModel,
                          MainWindowEvent mainWindowEvent, DependencyProvider dependencyProvider, CreateTemplateFromEventAction action, Runnable refresh) {
        super("Add event", Icons.ADD_ICON);
        this.eventTable = eventTable;
        this.eventValidator = Objects.requireNonNull(dependencyProvider.getEventValidator());
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateListModel = templateListModel;
        this.mainWindowEvent = mainWindowEvent;
        this.categoryCrudService = dependencyProvider.getCategoryCrudService();
        this.action = action;
        this.refresh = refresh;
        putValue(SHORT_DESCRIPTION, "Adds new event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var eventTableModel = mainWindowEvent.getTableModel();
        var dialog = new EventDialog(categoryCrudService, createPrefilledEvent(), categoryListModel, timeUnitListModel, templateListModel, false, eventValidator, action);
        dialog.show(eventTable, "Add Event").ifPresent(entity -> new AddActionSwingWorker<>(eventTableModel, refresh, entity).execute());
    }

    private Event createPrefilledEvent() {
        return new Event(
                UUID.randomUUID(),
                "Dinner",
                new Category(UUID.randomUUID(), "Social", Color.BLACK),
                LocalDate.now(),
                LocalTime.now(),
                new TimeUnit(UUID.randomUUID(), false, "CoffeeBreak", 0, 10),
                5,
                "Dinner with parents"
        );
    }
}
