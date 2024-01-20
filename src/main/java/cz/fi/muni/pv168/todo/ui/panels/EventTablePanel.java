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

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;

public class EventTablePanel extends TablePanel<Event> {

    public EventTablePanel(TableModel<Event> eventTableModel, BiConsumer<Integer, Boolean> onSelectionChange) {
        super(eventTableModel, onSelectionChange);
        table.addMouseListener(new DetailClick<>(eventTableModel, this::formatEvent, "Event detail"));
        setUpTable();
    }

    private String formatEvent(Event event) {
        return event.getName() + " " + event.getCategory().getName() + " " + event.getStartTime();
    }

    protected void setUpTable() {
        // Renderers bind
        table.setDefaultRenderer(Category.class, new CategoryRenderer());
        table.setDefaultRenderer(Status.class, new StatusRenderer());
        table.setDefaultRenderer(LocalDate.class, new LocalDateRenderer());
        table.setDefaultRenderer(Color.class, new CategoryColourRenderer());
        table.setDefaultRenderer(LocalDateTime.class, new LocalDateTimeRenderer());
        table.setAutoCreateRowSorter(true);

        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
    }
}
