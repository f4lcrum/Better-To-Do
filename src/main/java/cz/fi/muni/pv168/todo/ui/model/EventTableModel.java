package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.List;

public class EventTableModel extends AbstractTableModel implements EntityTableModel<Event> {

    private final List<Event> events;

    private final List<Column<Event, ?>> columns = List.of(
            Column.readonly(" ", Color.class, Event::getColour),
            Column.readonly("Name of the event", String.class, Event::getName),
            Column.readonly("Start date and Time", LocalDateTime.class, Event::calculateStart),
            Column.readonly("Category", Category.class, Event::getCategory),
            Column.readonly("Status", Status.class, Event::getStatus),
            Column.readonly("Duration (minutes)", Long.class, Event::getEventDuration)
    );

    public EventTableModel(List<Event> events) {
        this.events = events;
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
    }

    @Override
    public Event getEntity(int rowIndex) {
        return events.get(rowIndex);
    }

}
