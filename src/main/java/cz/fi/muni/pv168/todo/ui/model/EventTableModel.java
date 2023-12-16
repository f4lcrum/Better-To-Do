package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventTableModel extends AbstractTableModel implements EntityTableModel<Event> {

    private List<Event> events;
    private final CrudService<Event> eventCrudService;

    private final List<Column<Event, ?>> columns = List.of(
            new Column<>(" ", Color.class, Event::getColour),
            new Column<>("Name of the event", String.class, Event::getName),
            new Column<>("Start date and Time", LocalDateTime.class, Event::calculateStart),
            new Column<>("Category", Category.class, Event::getCategory),
            new Column<>("Status", Status.class, Event::getStatus),
            new Column<>("Duration (minutes)", String.class, this::getDuration)
            );

    private String getDuration(Event event) {
        return String.format("%d %s", event.getDuration(), event.getTimeUnit().getName());
    }

    public EventTableModel(CrudService<Event> eventCrudService) {
        this.eventCrudService = eventCrudService;
        this.events = new ArrayList<>(eventCrudService.findAll());
    }

    @Override
    public int getRowCount() {
        return events.size();
    }


    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var template = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(template);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getColumnType();
    }

    public void deleteRow(int rowIndex) {
        var eventToBeDeleted = getEntity(rowIndex);
        eventCrudService.deleteByGuid(eventToBeDeleted.getGuid());
        events.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Event event) {
        eventCrudService.create(event)
                .intoException();
        int newRowIndex = events.size();
        events.add(event);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Event event) {
        eventCrudService.update(event)
                .intoException();
        int rowIndex = events.indexOf(event);
        events.set(rowIndex, event);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.events = new ArrayList<>(eventCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public Event getEntity(int rowIndex) {
        return events.get(rowIndex);
    }
}
