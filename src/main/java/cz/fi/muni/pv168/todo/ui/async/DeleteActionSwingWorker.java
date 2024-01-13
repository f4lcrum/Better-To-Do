package cz.fi.muni.pv168.todo.ui.async;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.MainWindow;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.SwingWorker;
import java.util.stream.Stream;

public class DeleteActionSwingWorker<T extends Entity> extends SwingWorker<Void, Void> {

    private final TableModel<T> tableModel;
    private final MainWindow mainWindow;
    private final Stream<Integer> stream;

    public DeleteActionSwingWorker(TableModel<T> tableModel, MainWindow mainWindow, Stream<Integer> stream) {
        this.tableModel = tableModel;
        this.mainWindow = mainWindow;
        this.stream = stream;
    }

    @Override
    protected Void doInBackground() {
        stream.forEach(tableModel::deleteRow);
        mainWindow.refresh();
        return null;
    }
}
