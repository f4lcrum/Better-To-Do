package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.function.Consumer;


public class CategoryTablePanel extends JPanel {

    private final JTable eventTable;

    private final TableModel<Category> categoryTableModel;
    private final Consumer<Integer> onSelectionChange;

    public CategoryTablePanel(TableModel<Category> categoryTableModel, Consumer<Integer> onSelectionChange) {
        setLayout(new BorderLayout());
        this.onSelectionChange = onSelectionChange;
        eventTable = setUpTable(categoryTableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);
        this.categoryTableModel = categoryTableModel;
    }

    private JTable setUpTable(TableModel<Category> categoryTableModel) {
        var table = new JTable(categoryTableModel);

        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());

        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new DetailClick<>(categoryTableModel, Category::getName, "Category detail"));
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }

    public void refresh() {
        categoryTableModel.refresh();
    }

    public JTable getEventTable() {
        return eventTable;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();
        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }
}
