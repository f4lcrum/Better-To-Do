package cz.fi.muni.pv168.bosv.better_todo.ui.model;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Template;
import static java.time.temporal.ChronoUnit.MINUTES;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class TemplateTableModel extends AbstractTableModel implements EntityTableModel<Template> {

    private List<Template> templates;

    private final List<Column<Template, ?>> columns = List.of(
            Column.readonly(" ", Color.class, Template::getColour),
            Column.readonly("Name", String.class, Template::getName),
            Column.readonly("Category", Category.class, Template::getCategory),
            Column.readonly("Duration (minutes)", Long.class, this::getDuration),
            Column.readonly("Description", String.class, Template::getDescription)
    );

    private long getDuration(Template template) {
        return MINUTES.between(template.getStartTime(), template.getEndTime());
    }

    public TemplateTableModel() {
        this.templates = new ArrayList<>();
    }

    public TemplateTableModel(List<Template> templates) {
        this.templates = templates;
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
        return;
    }

    @Override
    public Template getEntity(int rowIndex) {
        return templates.get(rowIndex);
    }

}
