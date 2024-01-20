package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.strategy.TimeUnitButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.model.Column;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.model.TimeUnitListModel;
import cz.fi.muni.pv168.todo.ui.panels.TimeUnitTablePanel;
import cz.fi.muni.pv168.todo.wiring.DependencyProvider;

import java.util.List;
import java.util.function.BiConsumer;

public class MainWindowTimeUnit extends MainWindowEntityImpl<TimeUnit> {

    public MainWindowTimeUnit(DependencyProvider dependencyProvider, BiConsumer<Integer, Boolean> onSelectionChange, Runnable refresh) {
        this.tableModel = new TableModel<>(dependencyProvider.getTimeUnitCrudService(), List.of(
                new Column<>("Name", String.class, TimeUnit::getName),
                new Column<>("Hour count", Long.class, TimeUnit::getHours),
                new Column<>("Minute count", Long.class, TimeUnit::getMinutes)
        ));
        this.listModel = new TimeUnitListModel(dependencyProvider.getTimeUnitCrudService());
        this.tablePanel = new TimeUnitTablePanel(tableModel, onSelectionChange);
        this.buttonTabStrategy = new TimeUnitButtonTabStrategy(tablePanel.getTable(), dependencyProvider, this, refresh);
        tablePanel.getTable().setComponentPopupMenu(MainWindowHelper.createPopupMenu((buttonTabStrategy)));
    }
}
