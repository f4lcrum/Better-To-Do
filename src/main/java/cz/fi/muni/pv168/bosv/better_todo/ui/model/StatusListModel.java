package cz.fi.muni.pv168.bosv.better_todo.ui.model;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class StatusListModel extends AbstractListModel<Status> {

    private List<Status> statuses;

    public StatusListModel() {
        this.statuses = List.of(Status.values());
    }

    @Override
    public int getSize() { return statuses.size(); }

    @Override
    public Status getElementAt(int index) { return statuses.get(index); }
}
