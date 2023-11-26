package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.ui.action.mouseClick.TableRow;
import cz.fi.muni.pv168.todo.ui.model.EventTableModel;
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

    private final EventTableModel eventTableModel;

    public EventTablePanel(EventTableModel eventTableModel) {
        setLayout(new BorderLayout());
        eventTable = setUpTable(eventTableModel);
        eventTable.addMouseListener(new TableRow("Event detail"));
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        this.eventTableModel = eventTableModel;
    }

    private JTable setUpTable(EventTableModel eventTableModel) {
        var table = new JTable(eventTableModel);

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
