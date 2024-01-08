package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.ui.model.CategoryTableModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Color;


public class CategoryTablePanel extends JPanel {

    private final JTable eventTable;

    private final CategoryTableModel categoryTableModel;

    public CategoryTablePanel(CategoryTableModel categoryTableModel) {
        setLayout(new BorderLayout());
        eventTable = setUpTable(categoryTableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        this.categoryTableModel = categoryTableModel;
    }

    private JTable setUpTable(CategoryTableModel categoryTableModel) {
        var table = new JTable(categoryTableModel);

        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());

        table.setAutoCreateRowSorter(true);

        return table;
    }

    public void refresh() {
        categoryTableModel.refresh();
    }

    public JTable getEventTable() {
        return eventTable;
    }
}
