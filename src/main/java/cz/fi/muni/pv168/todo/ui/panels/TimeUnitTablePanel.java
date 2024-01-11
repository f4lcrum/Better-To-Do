package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.action.DetailClick;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.sql.Time;
import java.util.function.Consumer;

public class TimeUnitTablePanel extends JPanel {

    private final JTable timeUnitTable;
    private final TableModel<TimeUnit> timeUnitTableModel;
    private final Consumer<Integer> onSelectionChange;

    public TimeUnitTablePanel(TableModel<TimeUnit> timeUnitTableModel, Consumer<Integer> onSelectionChange) {
        setLayout(new BorderLayout());
        this.onSelectionChange = onSelectionChange;
        this.timeUnitTable = setUpTable(timeUnitTableModel);
        this.timeUnitTableModel = timeUnitTableModel;
        add(new JScrollPane(timeUnitTable), BorderLayout.CENTER);
    }

    private JTable setUpTable(TableModel<TimeUnit> timeUnitTableModel) {
        var table = new JTable(timeUnitTableModel);

        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new DetailClick<>(timeUnitTableModel, TimeUnit::getName, "Time Unit detail"));
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }

    public void refresh() {
        timeUnitTableModel.refresh();
    }

    public JTable getEventTable() {
        return timeUnitTable;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();
        if (onSelectionChange != null) {
            onSelectionChange.accept(count);
        }
    }
}
