package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Status;
import cz.fi.muni.pv168.todo.ui.action.mouseClick.TableRow;
import cz.fi.muni.pv168.todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.LocalDateRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.LocalDateTimeRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.StatusRenderer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class EventTablePanel extends JPanel {

    private final JTable eventTable;

    private final TodoTableModel todoTableModel;

    public EventTablePanel(TodoTableModel todoTableModel) {
        setLayout(new BorderLayout());
        eventTable = setUpTable(todoTableModel);
        eventTable.addMouseListener(new TableRow("Event detail"));
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        this.todoTableModel = todoTableModel;
    }

    private JTable setUpTable(TodoTableModel todoTableModel) {
        var table = new JTable(todoTableModel);

        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(Status.class, new StatusRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());
        table.setDefaultRenderer(LocalDateTime.class, new LocalDateTimeRenderer());
        table.setAutoCreateRowSorter(true);

        return table;
    }

    public JTable getEventTable() {
        return eventTable;
    }
}
