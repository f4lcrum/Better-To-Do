package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;

import java.awt.Color;
import java.util.function.BiConsumer;

public class CategoryTablePanel extends BasePanel<Category> {

    public CategoryTablePanel(TableModel<Category> categoryTableModel, BiConsumer<Integer, Boolean> onSelectionChange) {
        super(categoryTableModel, onSelectionChange);
        setUpTable();
    }

    private void setUpTable() {
        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());

        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new DetailClick<>(tableModel, Category::getName, "Category detail"));
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
    }
}
