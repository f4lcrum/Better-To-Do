package cz.fi.muni.pv168.todo.ui.async;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.SwingWorker;

public class AddActionSwingWorker<T extends Entity> extends SwingWorker<Void, Void> {

    private final TableModel<T> tableModel;
    private final Runnable refresh;
    private final T entity;

    public AddActionSwingWorker(TableModel<T> tableModel, Runnable refresh, T entity) {
        this.tableModel = tableModel;
        this.refresh = refresh;
        this.entity = entity;
    }

    @Override
    protected Void doInBackground() {
        tableModel.addRow(entity);
        refresh.run();
        return null;
    }
}
