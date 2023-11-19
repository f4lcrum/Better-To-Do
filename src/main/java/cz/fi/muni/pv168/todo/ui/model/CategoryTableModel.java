package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.entity.Category;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class CategoryTableModel extends AbstractTableModel implements EntityTableModel<Category> {

    private final List<Category> categories;

    private final List<Column<Category, ?>> columns = List.of(
            Column.readonly(" ", Color.class, Category::getColour),
            Column.readonly("Name", String.class, Category::getName)
    );

    public CategoryTableModel() {
        this.categories = new ArrayList<>();
    }

    public CategoryTableModel(List<Category> categories) {
        this.categories = categories;
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
        return;
    }

    @Override
    public Category getEntity(int rowIndex) {
        return categories.get(rowIndex);
    }

}
