package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.storage.sql.dao.DataStorageException;

import javax.swing.JOptionPane;
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

    public void deleteRow(int rowIndex) {
        var entityToBeDeleted = getEntity(rowIndex);
        if (entityToBeDeleted.isDefault()) {
            return;
        }
        try {
            crudService.deleteByGuid(entityToBeDeleted.getGuid());
            content.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        } catch(DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Unable to delete, this item is used elsewhere", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public int getColumnIndex(Column<T, ?> column) {
        return columns.indexOf(column);
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

    public void refresh() {
        this.content = new ArrayList<>(
                crudService.findAll());
        fireTableDataChanged();
    }
}
