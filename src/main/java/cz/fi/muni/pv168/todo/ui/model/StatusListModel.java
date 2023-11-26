package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Status;

import javax.swing.AbstractListModel;
import java.util.List;

public class StatusListModel extends AbstractListModel<Status> {

    private final List<Status> statuses;

    public StatusListModel() {
        this.statuses = List.of(Status.values());
    }

    @Override
    public int getSize() {
        return statuses.size();
    }

    @Override
    public Status getElementAt(int index) {
        return statuses.get(index);
    }
}
