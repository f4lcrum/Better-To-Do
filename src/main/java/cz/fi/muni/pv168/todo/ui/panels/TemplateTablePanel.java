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
import java.util.function.Consumer;

public class TemplateTablePanel extends BasePanel<Template> {

    public TemplateTablePanel(TableModel<Template> templateTableModel, Consumer<Integer> onSelectionChange) {
        super(templateTableModel, onSelectionChange);
        setUpTable(templateTableModel);
    }

    private void setUpTable(TableModel<Template> templateTableModel) {
        table.setAutoCreateRowSorter(true);
        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());
        table.addMouseListener(new DetailClick<>(templateTableModel, Template::getName, "Template detail"));

        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
    }
}
