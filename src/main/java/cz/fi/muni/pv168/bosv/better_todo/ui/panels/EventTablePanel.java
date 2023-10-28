package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.LocalDateRenderer;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.StatusRenderer;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

@NonNull
public class EventTablePanel extends JPanel {
    @Getter
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

        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(Status.class, new StatusRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());

        table.setAutoCreateRowSorter(true);

        return table;
    }
}
