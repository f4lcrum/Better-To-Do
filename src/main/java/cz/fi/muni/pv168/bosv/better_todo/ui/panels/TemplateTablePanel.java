package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.LocalDateRenderer;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

@NonNull
public class TemplateTablePanel extends AbstractEntityResizeableJPanel {

    public TemplateTablePanel(TemplateTableModel templateTableModel) {
        super(templateTableModel);
        setUpTable();
        setLayout(new BorderLayout());
        add(new JScrollPane(getTable()), BorderLayout.CENTER);
    }

    private void setUpTable() {
        JTable table = getTable();

        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());

        table.setAutoCreateRowSorter(true);
    }
}
