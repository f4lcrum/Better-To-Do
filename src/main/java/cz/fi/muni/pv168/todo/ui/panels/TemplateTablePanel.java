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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.util.function.Consumer;

public class TemplateTablePanel extends JPanel {

    private final JTable eventTable;
    private final TableModel<Template> templateTableModel;
    private final Consumer<Integer> onSelectionChange;

    public TemplateTablePanel(TableModel<Template> templateTableModel, Consumer<Integer> onSelectionChange) {
        setLayout(new BorderLayout());
        this.onSelectionChange = onSelectionChange;
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

        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

        return table;
    }

    public void refresh() {
        templateTableModel.refresh();
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
