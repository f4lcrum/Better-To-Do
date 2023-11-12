package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.dialog.EventDialog;
import cz.fi.muni.pv168.todo.ui.model.EventTableModel;
import cz.fi.muni.pv168.todo.ui.renderer.SpecialTemplateValues;
import cz.fi.muni.pv168.todo.ui.resources.Icons;
import cz.fi.muni.pv168.todo.util.Either;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public final class AddEventAction extends AbstractAction {

    private final JTable eventTable;

    private final ListModel<Category> categoryListModel;
    private final ListModel<Either<Template, SpecialTemplateValues>> templateList;

    public AddEventAction(JTable eventTable, ListModel<Category> categoryListModel, ListModel<Either<Template, SpecialTemplateValues>> templateList) {
        super("Add event", Icons.ADD_ICON);
        this.eventTable = eventTable;
        this.categoryListModel = categoryListModel;
        this.templateList = templateList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var eventTableModel = (EventTableModel) eventTable.getModel();
        var dialog = new EventDialog(createPrefilledEvent(), categoryListModel, templateList);
        dialog.show(eventTable, "Add Event");
    }

    private Event createPrefilledEvent() {
        return new Event(
                UUID.randomUUID(),
                "Dinner",
                new Category(UUID.randomUUID(), "Social", Color.BLACK),
                LocalDate.now(),
                LocalTime.now(),
                new TimeUnit(UUID.randomUUID(), "CoffeBreak", 0, 10),
                5,
                "Dinner with parents"
        );
    }
}
