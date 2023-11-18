package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.ui.action.mouseClick.TableRow;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.LocalDateRenderer;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TemplateTablePanel extends JPanel {
    @Getter
    private final JTable eventTable;
    private final TemplateTableModel templateTableModel;

    public TemplateTablePanel(TemplateTableModel templateTableModel) {
        setLayout(new BorderLayout());
        eventTable = setUpTable(templateTableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        this.templateTableModel = templateTableModel;
    }

    private JTable setUpTable(TemplateTableModel templateTableModel) {
        var table = new JTable(templateTableModel);

        table.setAutoCreateRowSorter(true);
        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());
        table.addMouseListener(new TableRow("Template detail"));
        return table;
    }
}
