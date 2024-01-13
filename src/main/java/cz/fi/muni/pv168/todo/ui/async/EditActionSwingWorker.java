package cz.fi.muni.pv168.todo.ui.async;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.SwingWorker;

public class EditActionSwingWorker<T extends Entity> extends SwingWorker<Void, Void> {

    private final TableModel<T> tableModel;
    private final MainWindow mainWindow;
    private final T entity;

    public EditActionSwingWorker(TableModel<T> tableModel, MainWindow mainWindow, T entity) {
        this.tableModel = tableModel;
        this.mainWindow = mainWindow;
        this.entity = entity;
    }

    @Override
    protected Void doInBackground() {
        tableModel.updateRow(entity);
        mainWindow.refresh();
        return null;
    }
}
