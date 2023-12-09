package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;

import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TimeUnitTableModel extends AbstractTableModel implements EntityTableModel<TimeUnit> {

    private List<TimeUnit> timeUnits;
    private final CrudService<TimeUnit> timeUnitCrudService;

    private final List<Column<TimeUnit, ?>> columns = List.of(
            Column.readonly("Name", String.class, TimeUnit::getName),
            Column.readonly("Hour count", Long.class, TimeUnit::getHourCount),
            Column.readonly("Minute count", Long.class, TimeUnit::getMinuteCount)
    );

    public TimeUnitTableModel(CrudService<TimeUnit> timeUnitCrudService) {
        this.timeUnitCrudService = timeUnitCrudService;
        this.timeUnits = new ArrayList<>(timeUnitCrudService.findAll());
    }

    @Override
    public int getRowCount() {
        return timeUnits.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var timeUnit = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(timeUnit);
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

    public void deleteRow(int rowIndex) {
        var timeUnitToBeDeleted = getEntity(rowIndex);
        timeUnitCrudService.deleteByGuid(timeUnitToBeDeleted.getGuid().toString());
        timeUnits.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(TimeUnit timeUnit) {
        timeUnitCrudService.create(timeUnit)
                .intoException();
        int newRowIndex = timeUnits.size();
        timeUnits.add(timeUnit);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(TimeUnit timeUnit) {
        timeUnitCrudService.update(timeUnit)
                .intoException();
        int rowIndex = timeUnits.indexOf(timeUnit);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.timeUnits = new ArrayList<>(timeUnitCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public TimeUnit getEntity(int rowIndex) {
        return timeUnits.get(rowIndex);
    }
}
