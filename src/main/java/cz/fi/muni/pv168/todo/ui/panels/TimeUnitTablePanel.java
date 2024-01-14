package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import java.util.function.BiConsumer;

public class TimeUnitTablePanel extends BasePanel<TimeUnit> {

    public TimeUnitTablePanel(TableModel<TimeUnit> timeUnitTableModel, BiConsumer<Integer, Boolean> onSelectionChange) {
        super(timeUnitTableModel, onSelectionChange);
        setUpTable();
    }

    private void setUpTable() {
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new DetailClick<>(tableModel, TimeUnit::getName, "Time Unit detail"));
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
    }
}
