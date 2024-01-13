package cz.fi.muni.pv168.todo.ui.async;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.SwingWorker;

public class AddActionSwingWorker<T extends Entity> extends SwingWorker<Void, Void> {

    private final TableModel<T> tableModel;
    private final MainWindow mainWindow;
    private final T entity;

    public AddActionSwingWorker(TableModel<T> tableModel, MainWindow mainWindow, T entity) {
        this.tableModel = tableModel;
        this.mainWindow = mainWindow;
        this.entity = entity;
    }

    @Override
    protected Void doInBackground() {
        tableModel.addRow(entity);
        mainWindow.refresh();
        return null;
    }
}
