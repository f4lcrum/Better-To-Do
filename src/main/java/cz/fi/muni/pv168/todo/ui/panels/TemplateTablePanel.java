package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.LocalDateRenderer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;

public class TemplateTablePanel extends JPanel {

    private final JTable eventTable;
    private final TableModel<Template> templateTableModel;

    public TemplateTablePanel(TableModel<Template> templateTableModel) {
        setLayout(new BorderLayout());
        eventTable = setUpTable(templateTableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        this.templateTableModel = templateTableModel;
    }

    private JTable setUpTable(TableModel<Template> templateTableModel) {
        var table = new JTable(templateTableModel);

        table.setAutoCreateRowSorter(true);
        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());
        table.addMouseListener(new DetailClick<>(templateTableModel, Template::getName, "Template detail"));
        return table;
    }

    public void refresh() {
        templateTableModel.refresh();
    }

    public JTable getEventTable() {
        return eventTable;
    }
}
