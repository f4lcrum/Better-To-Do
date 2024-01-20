package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.LocalDateRenderer;

import java.awt.Color;
import java.time.LocalDate;
import java.util.function.BiConsumer;

public class TemplateTablePanel extends TablePanel<Template> {

    public TemplateTablePanel(TableModel<Template> templateTableModel, BiConsumer<Integer, Boolean> onSelectionChange) {
        super(templateTableModel, onSelectionChange);
        setUpTable();
    }

    protected void setUpTable() {
        table.setAutoCreateRowSorter(true);
        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());
        table.addMouseListener(new DetailClick<>(tableModel, Template::getName, "Template detail"));

        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
    }
}
