package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TimeUnitTableModel extends AbstractTableModel implements EntityTableModel<TimeUnit> {

    private List<TimeUnit> timeUnits;
    private final CrudService<TimeUnit> timeUnitCrudService;

    private final List<Column<TimeUnit, ?>> columns = List.of(
            new Column<>("Name", String.class, TimeUnit::getName),
            new Column<>("Hour count", Long.class, TimeUnit::getHourCount),
            new Column<>("Minute count", Long.class, TimeUnit::getMinuteCount)
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
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    }

    public void deleteRow(int rowIndex) {
        var timeUnitToBeDeleted = getEntity(rowIndex);
        if (timeUnitToBeDeleted.isDefault()) {
            return;
        }
        timeUnitCrudService.deleteByGuid(timeUnitToBeDeleted.getGuid());
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
        if (timeUnit.isDefault()) {
            return;
        }
        timeUnitCrudService.update(timeUnit)
                .intoException();
        int rowIndex = timeUnits.indexOf(timeUnit);
        timeUnits.set(rowIndex, timeUnit);
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
