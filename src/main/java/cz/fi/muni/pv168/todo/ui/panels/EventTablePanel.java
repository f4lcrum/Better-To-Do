package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.entity.*;
import cz.fi.muni.pv168.todo.ui.action.mouseClick.*;
import cz.fi.muni.pv168.todo.ui.model.*;
import cz.fi.muni.pv168.todo.ui.renderer.*;
import lombok.*;

import javax.swing.*;
import java.awt.*;
import java.time.*;

@NonNull
public class EventTablePanel extends JPanel {
    @Getter
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
}
