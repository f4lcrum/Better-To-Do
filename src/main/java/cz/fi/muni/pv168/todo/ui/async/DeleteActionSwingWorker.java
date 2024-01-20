package cz.fi.muni.pv168.todo.ui.async;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.model.TableModel;

import javax.swing.SwingWorker;
import java.util.stream.Stream;

public class DeleteActionSwingWorker<T extends Entity> extends SwingWorker<Void, Void> {

    private final TableModel<T> tableModel;
    private final Runnable refresh;
    private final Stream<Integer> stream;

    public DeleteActionSwingWorker(TableModel<T> tableModel, Runnable refresh, Stream<Integer> stream) {
        this.tableModel = tableModel;
        this.refresh = refresh;
        this.stream = stream;
    }

    @Override
    protected Void doInBackground() {
        stream.forEach(tableModel::deleteRow);
        refresh.run();
        return null;
    }
}
