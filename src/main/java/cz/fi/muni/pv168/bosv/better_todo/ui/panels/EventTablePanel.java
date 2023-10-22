package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;

import javax.swing.*;
import java.awt.*;

public class EventTablePanel extends JPanel {

    private final JTable eventTable;

    private final TodoTableModel todoTableModel;

    public EventTablePanel(TodoTableModel todoTableModel) {
        setLayout(new BorderLayout());
        eventTable = setUpTable(todoTableModel);
        add(new JScrollPane(eventTable), BorderLayout.CENTER);

        this.todoTableModel = todoTableModel;
    }

    private JTable setUpTable(TodoTableModel todoTableModel) {
        var table = new JTable(todoTableModel);

        table.setAutoCreateRowSorter(true);

        return table;
    }
}
