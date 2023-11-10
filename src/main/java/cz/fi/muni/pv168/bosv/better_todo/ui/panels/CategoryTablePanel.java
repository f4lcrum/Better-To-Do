package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.CategoryTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.LocalDateRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.StatusRenderer;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

@NonNull
public class CategoryTablePanel extends JPanel {
    @Getter
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
}
