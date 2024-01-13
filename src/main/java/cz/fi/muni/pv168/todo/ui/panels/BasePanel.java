package cz.fi.muni.pv168.todo.ui.panels;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import java.awt.BorderLayout;
import java.util.function.BiConsumer;

public abstract class BasePanel<T extends Entity> extends JPanel {

    protected final JTable table;
    protected final TableModel<T> tableModel;
    protected final BiConsumer<Integer, Boolean> onSelectionChange;

    protected BasePanel(TableModel<T> tableModel, BiConsumer<Integer, Boolean> onSelectionChange) {
        setLayout(new BorderLayout());
        this.table = new JTable(tableModel);
        this.tableModel = tableModel;
        this.onSelectionChange = onSelectionChange;
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refresh() {
        tableModel.refresh();
    }

    public JTable getTable() {
        return table;
    }

    protected void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();

        var rows = table.getSelectedRows();
        var enabled = true;
        for (int row : rows) {
            var entity = tableModel.getEntity(row);
            if (entity.isDefault()) {
                enabled = false;
            }
        }

        if (onSelectionChange != null) {
            onSelectionChange.accept(count, enabled);
        }
    }
}
