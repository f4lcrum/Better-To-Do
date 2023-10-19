package cz.muni.fi.pv168.Entity.ui.panels;

import cz.muni.fi.pv168.Entity.ui.model.EntityTableModel;
import cz.muni.fi.pv168.Entity.ui.model.TodoTableModel;

import javax.swing.*;
import java.awt.*;

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
