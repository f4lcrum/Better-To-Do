package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.Status;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoTableModel extends AbstractTableModel implements EntityTableModel<cz.fi.muni.pv168.todo.entity.Event> {

    private List<cz.fi.muni.pv168.todo.entity.Event> events;

    private final List<Column<cz.fi.muni.pv168.todo.entity.Event, ?>> columns = List.of(
            Column.readonly(" ", Color.class, cz.fi.muni.pv168.todo.entity.Event::getColour),
            Column.readonly("Name of the event", String.class, cz.fi.muni.pv168.todo.entity.Event::getName),
            Column.readonly("Start date and Time", LocalDateTime.class, cz.fi.muni.pv168.todo.entity.Event::calculateStart),
            Column.readonly("Category", Category.class, cz.fi.muni.pv168.todo.entity.Event::getCategory),
            Column.readonly("Status", Status.class, cz.fi.muni.pv168.todo.entity.Event::getStatus),
            Column.readonly("Duration (minutes)", Long.class, cz.fi.muni.pv168.todo.entity.Event::getEventDuration)
    );

    public TodoTableModel() {
        this.events = new ArrayList<>();
    }

    public TodoTableModel(List<cz.fi.muni.pv168.todo.entity.Event> events) {
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

    public void getColumnModel() {
        return;
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
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        return;
    }

    @Override
    public Event getEntity(int rowIndex) {
        return events.get(rowIndex);
    }

}
