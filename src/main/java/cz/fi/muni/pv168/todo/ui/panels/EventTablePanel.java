package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryColourRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.LocalDateRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.LocalDateTimeRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.StatusRenderer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Consumer;


public class EventTablePanel extends JPanel {

    private final JTable eventTable;
    private final TableModel<Event> eventTableModel;
    private final Consumer<Integer> onSelectionChange;

    public EventTablePanel(TableModel<Event> eventTableModel, Consumer<Integer> onSelectionChange) {
        setLayout(new BorderLayout());
        this.onSelectionChange = onSelectionChange;
        eventTable = setUpTable(eventTableModel);
        eventTable.addMouseListener(new DetailClick<>(eventTableModel, this::formatEvent, "Event detail"));
        add(new JScrollPane(eventTable), BorderLayout.CENTER);
        this.eventTableModel = eventTableModel;
    }

    private String formatEvent(Event event) {
        return event.getName() + " " + event.getCategory().getName() + " " + event.getStartTime();
    }
    private JTable setUpTable(TableModel<Event> eventTableModel) {

        var table = new JTable(eventTableModel);

        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(Status.class, new StatusRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());
        table.setDefaultRenderer(LocalDateTime.class, new LocalDateTimeRenderer());
        table.setAutoCreateRowSorter(true);

        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

        return table;
    }

    public void refresh() {
        eventTableModel.refresh();
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
