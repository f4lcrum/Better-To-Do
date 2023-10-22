package cz.fi.muni.pv168.bosv.better_todo.ui.model;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import static java.time.temporal.ChronoUnit.MINUTES;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoTableModel extends AbstractTableModel implements EntityTableModel<Event> {

    private List<Event> events;

    private final List<Column<Event, ?>> columns = List.of(
            Column.readonly("Name", String.class, Event::getName),
            Column.readonly("Date", LocalDate.class, Event::getDate),
            Column.readonly("Category", Category.class, Event::getCategory),
            Column.readonly("Status", Status.class, Event::getStatus),
            Column.readonly("Duration", Long.class, this::getDuration),
            Column.readonly("Description", String.class, Event::getDescription)
    );

    private long getDuration(Event event) {
        return MINUTES.between(event.getStartTime(), event.getEndTime());
    }

    public TodoTableModel() {
        this.events = new ArrayList<>();
    }

    public TodoTableModel(List<Event> events) {
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
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        return;
    }

    @Override
    public Event getEntity(int rowIndex) {
        return events.get(rowIndex);
    }

}
