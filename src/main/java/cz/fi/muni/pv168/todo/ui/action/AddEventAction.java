package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.resources.Icons;

import java.util.Objects;
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
import java.util.UUID;

public final class AddEventAction extends AbstractAction {

    private final JTable todoTable;
    private final ListModel<Category> categoryListModel;
    private final ListModel<TimeUnit> timeUnitListModel;
    private final ListModel<Template> templateListModel;
    private final Validator<Event> eventValidator;
    private final MainWindow mainWindow;

    public AddEventAction(JTable todoTable, ListModel<Category> categoryListModel, ListModel<TimeUnit> timeUnitListModel,
                          ListModel<Template> templateListModel, MainWindow mainWindow) {
        super("Add event", Icons.ADD_ICON);
        this.todoTable = todoTable;
        this.eventValidator = Objects.requireNonNull(mainWindow.getEventValidator());
        this.categoryListModel = categoryListModel;
        this.timeUnitListModel = timeUnitListModel;
        this.templateListModel = templateListModel;
        this.mainWindow = mainWindow;
        putValue(SHORT_DESCRIPTION, "Adds new event");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        putValue(Action.SMALL_ICON, Icons.ADD_ICON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var eventTableModel = mainWindow.getEventTableModel();
        var dialog = new EventDialog(mainWindow.getCategoryCrudService(), createPrefilledEvent(), categoryListModel, timeUnitListModel, templateListModel, false, eventValidator);
        dialog.show(todoTable, "Add Event")
                .ifPresent(eventTableModel::addRow);
        mainWindow.refreshEventListModel();
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
