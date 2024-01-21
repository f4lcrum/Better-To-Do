package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.strategy.EventButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.EventListModel;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import javax.swing.ListModel;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

public class MainWindowEvent extends MainWindowEntityImpl<Event> {

    private final Runnable refreshStatistics;

    public MainWindowEvent(DependencyProvider dependencyProvider, BiConsumer<Integer, Boolean> onSelectionChange, ListModel<Category> categoryListModel, ListModel<Template> templateListModel,
                           ListModel<TimeUnit> timeUnitListModel, Column<Event, LocalDateTime> startDateRow, Runnable refreshStatistics, Runnable refresh) {
        this.tableModel = new TableModel<>(dependencyProvider.getEventCrudService(), List.of(
                new Column<>(" ", Color.class, Event::getColour),
                new Column<>("Name of the event", String.class, Event::getName),
                startDateRow,
                new Column<>("Category", Category.class, Event::getCategory),
                new Column<>("Status", Status.class, Event::getStatus),
                new Column<>("Duration (minutes)", String.class, Event::getDurationString)
        ));
        this.listModel = new EventListModel(dependencyProvider.getEventCrudService());
        this.tablePanel = new EventTablePanel(tableModel, onSelectionChange);
        this.buttonTabStrategy = new EventButtonTabStrategy(tablePanel.getTable(), categoryListModel, timeUnitListModel, templateListModel, this, dependencyProvider, refresh);
        this.refreshStatistics = refreshStatistics;
        tablePanel.getTable().setComponentPopupMenu(MainWindowHelper.createPopupMenu((buttonTabStrategy)));
    }

    @Override
    public void refreshModel() {
        super.refreshModel();
        refreshStatistics.run();
    }
}
