package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class CategoryTableModel extends AbstractTableModel implements EntityTableModel<Category> {

    private List<Category> categories;
    private final CrudService<Category> categoryCrudService;

    private final List<Column<Category, ?>> columns = List.of(
            Column.readonly(" ", Color.class, Category::getColor),
            Column.readonly("Name", String.class, Category::getName)
    );

    public CategoryTableModel(CrudService<Category> categoryCrudService) {
        this.categoryCrudService = categoryCrudService;
        this.categories = new ArrayList<>(categoryCrudService.findAll());
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var category = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(category);
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
    }

    public void deleteRow(int rowIndex) {
        var categoryToBeDeleted = getEntity(rowIndex);
        categoryCrudService.deleteByGuid(categoryToBeDeleted.getGuid());
        categories.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Category category) {
        categoryCrudService.create(category)
                .intoException();
        int newRowIndex = categories.size();
        categories.add(category);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Category category) {
        categoryCrudService.update(category)
                .intoException();
        int rowIndex = categories.indexOf(category);
        categories.set(rowIndex, category);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.categories = new ArrayList<>(categoryCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public Category getEntity(int rowIndex) {
        return categories.get(rowIndex);
    }

}
