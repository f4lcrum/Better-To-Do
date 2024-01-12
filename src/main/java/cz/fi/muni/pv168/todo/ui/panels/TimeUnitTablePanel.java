package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.event.ListSelectionEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TimeUnitTablePanel extends BasePanel<TimeUnit> {

    private final BiConsumer<Boolean, Boolean> specialOnSelectionChanged;

    public TimeUnitTablePanel(TableModel<TimeUnit> timeUnitTableModel, Consumer<Integer> onSelectionChange,
                              BiConsumer<Boolean, Boolean> specialOnSelectionChanged) {
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
        var edit = true;
        var delete = false;
        for (int row : rows) {
            var entity = tableModel.getEntity(row);
            if (entity.isDefault()) {
                edit = false;
            } else {
                delete = true;
            }
        }
        specialOnSelectionChanged.accept(edit, delete);
    }
}
