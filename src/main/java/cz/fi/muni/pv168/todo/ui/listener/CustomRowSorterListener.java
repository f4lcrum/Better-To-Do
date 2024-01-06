package cz.fi.muni.pv168.todo.ui.listener;

import cz.fi.muni.pv168.todo.ui.model.EventTableModel;
import cz.fi.muni.pv168.todo.ui.panels.StatisticsPanel;

import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;

public class CustomRowSorterListener implements RowSorterListener {

    private final StatisticsPanel statistics;
    private final TableRowSorter<EventTableModel> rowSorter;

    public CustomRowSorterListener(StatisticsPanel statistics, TableRowSorter<EventTableModel> rowSorter) {
        super();
        this.statistics = statistics;
        this.rowSorter = rowSorter;
    }

    @Override
    public void sorterChanged(RowSorterEvent e) {
        var count = rowSorter.getViewRowCount();
        var indices = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
            indices.add(rowSorter.convertRowIndexToModel(i));
        }

        statistics.refreshFilter(indices);
    }
}
