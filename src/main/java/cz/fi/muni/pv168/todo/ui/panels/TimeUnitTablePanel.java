package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.sql.Time;

public class TimeUnitTablePanel extends JPanel {

    private final JTable timeUnitTable;
    private final TableModel<TimeUnit> timeUnitTableModel;

    public TimeUnitTablePanel(TableModel<TimeUnit> timeUnitTableModel) {
        setLayout(new BorderLayout());
        this.timeUnitTable = setUpTable(timeUnitTableModel);
        this.timeUnitTableModel = timeUnitTableModel;
        add(new JScrollPane(timeUnitTable), BorderLayout.CENTER);
    }

    private JTable setUpTable(TableModel<TimeUnit> timeUnitTableModel) {
        var table = new JTable(timeUnitTableModel);

        table.setAutoCreateRowSorter(true);

        return table;
    }

    public void refresh() {
        timeUnitTableModel.refresh();
    }

    public JTable getEventTable() {
        return timeUnitTable;
    }
}
