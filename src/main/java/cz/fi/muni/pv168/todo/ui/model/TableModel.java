package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel<T extends Entity> extends AbstractTableModel implements EntityTableModel<T> {
    private List<T> content;
    private final CrudService<T> crudService;

    private final List<Column<T, ?>> columns;

    public TableModel(CrudService<T> crudService, List<Column<T, ?>> columns) {
        this.crudService = crudService;
        this.columns = columns;
        this.content = new ArrayList<>(crudService.findAll());
    }
    @Override
    public int getRowCount() {
        return content.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var item = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(item);
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
        var categoryToBeDeleted = getEntity(rowIndex);
        crudService.deleteByGuid(categoryToBeDeleted.getGuid());
        content.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(T item) {
        crudService.create(item)
                .intoException();
        int newRowIndex = content.size();
        content.add(item);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(T item) {
        crudService.update(item)
                .intoException();
        int rowIndex = content.indexOf(item);
        content.set(rowIndex, item);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    @Override
    public T getEntity(int rowIndex) {
        return content.get(rowIndex);
    }
}
