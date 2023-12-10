package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class TemplateTableModel extends AbstractTableModel implements EntityTableModel<Template> {

    private List<Template> templates;
    private final CrudService<Template> templateCrudService;

    private final List<Column<Template, ?>> columns = List.of(
            Column.readonly(" ", Color.class, Template::getColour),
            Column.readonly("Template name", String.class, Template::getName),
            Column.readonly("Category", Category.class, Template::getCategory),
            Column.readonly("Duration", String.class, this::getDuration)
    );

    private String getDuration(Template template) {
        return String.format("%d %s", template.getTimeUnitCount(), template.getTimeUnit().getName());
    }

    public TemplateTableModel(CrudService<Template> templateCrudService) {
        this.templateCrudService = templateCrudService;
        this.templates = new ArrayList<>(templateCrudService.findAll());
    }

    @Override
    public int getRowCount() {
        return templates.size();
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
    }

    public void deleteRow(int rowIndex) {
        var templateToBeDeleted = getEntity(rowIndex);
        templateCrudService.deleteByGuid(templateToBeDeleted.getGuid());
        templates.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Template template) {
        templateCrudService.create(template)
                .intoException();
        int newRowIndex = templates.size();
        templates.add(template);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Template template) {
        templateCrudService.update(template)
                .intoException();
        int rowIndex = templates.indexOf(template);
        templates.set(rowIndex, template);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.templates = new ArrayList<>(templateCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public Template getEntity(int rowIndex) {
        return templates.get(rowIndex);
    }

}
