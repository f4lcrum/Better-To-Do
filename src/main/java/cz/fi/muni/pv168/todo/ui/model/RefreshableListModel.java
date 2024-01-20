package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Entity;

import javax.swing.AbstractListModel;

public abstract class RefreshableListModel<T extends Entity> extends AbstractListModel<T> {
    public abstract void refresh();
}
