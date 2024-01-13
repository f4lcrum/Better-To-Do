package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.event.ListSelectionEvent;
import java.util.function.Consumer;

public class TimeUnitTablePanel extends BasePanel<TimeUnit> {

    private final Consumer<Boolean> specialOnSelectionChanged;

    public TimeUnitTablePanel(TableModel<TimeUnit> timeUnitTableModel, Consumer<Integer> onSelectionChange,
                              Consumer<Boolean> specialOnSelectionChanged) {
        super(timeUnitTableModel, onSelectionChange);
        this.specialOnSelectionChanged = specialOnSelectionChanged;
        setUpTable();
    }

    private void setUpTable() {
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new DetailClick<>(tableModel, TimeUnit::getName, "Time Unit detail"));
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
    }

    @Override
    protected void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        super.rowSelectionChanged(listSelectionEvent);
        var rows = table.getSelectedRows();
        var enabled = true;
        for (int row : rows) {
            var entity = tableModel.getEntity(row);
            if (entity.isDefault()) {
                enabled = false;
            }
        }
        specialOnSelectionChanged.accept(enabled);
    }
}
