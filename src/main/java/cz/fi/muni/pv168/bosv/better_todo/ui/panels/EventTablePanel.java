package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class EventTablePanel extends JPanel {

    private final JTable table;

    private final TodoTableModel todoTableModel;

    public EventTablePanel(TodoTableModel todoTableModel) {
        setLayout(new BorderLayout());
        table = setUpTable(todoTableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        this.todoTableModel = todoTableModel;
    }

    private JTable setUpTable(TodoTableModel todoTableModel) {
        var table = new JTable(todoTableModel);

        table.setAutoCreateRowSorter(true);

        return table;
    }
}
