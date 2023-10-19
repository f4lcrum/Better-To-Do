package cz.muni.fi.pv168.Entity.ui.model;

import cz.muni.fi.pv168.Entity.Category;
import cz.muni.fi.pv168.Entity.Event;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoTableModel extends AbstractTableModel implements EntityTableModel<Event> {

    private List<Event> events;

    private final List<Column<Event, ?>> columns = List.of(
            Column.readonly("Name", String.class, this::getName),
            Column.readonly("Date", LocalDate.class, this::getDate),
            Column.readonly("Category", String.class, this::getCategory),
            Column.readonly("Status", String.class, this::getStatus),
            Column.readonly("Duration", LocalDateTime.class, this::getDuration),
            Column.readonly("Description", String.class, this::getDescription)
    );


    private String getStatus(Event event) {
        return "Status fajnovy";
    }

    private String getDescription(Event event) {
        return "Popis fajnovy";
    }
    private LocalDateTime getDuration(Event event) {
        return LocalDateTime.now();
    }

    private String getName(Event event) {
        return "NAME";
    }

    private String getCategory(Event event) {
        return "nice category";
    }

    private LocalDate getDate(Event event) {
        return LocalDate.now();
    }

    public TodoTableModel() {
        this.events = new ArrayList<>();
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
        var employee = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(employee);
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
        /* Commented out for test purposes
        if (value != null) {
            var event = getEntity(rowIndex);
            columns.get(columnIndex).setValue(value, event);
            updateRow(event);
        }*/
    }
    @Override
    public Event getEntity(int rowIndex) {
        return events.get(rowIndex);
    }

}
