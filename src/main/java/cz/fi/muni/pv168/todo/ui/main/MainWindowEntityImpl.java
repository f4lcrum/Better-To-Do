package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.action.strategy.ButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.model.RefreshableListModel;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.panels.TablePanel;

public abstract class MainWindowEntityImpl<T extends Entity> implements MainWindowEntity<T> {

    protected RefreshableListModel<T> listModel;
    protected TableModel<T> tableModel;
    protected TablePanel<T> tablePanel;
    protected ButtonTabStrategy buttonTabStrategy;

    @Override
    public void refreshModel() {
        listModel.refresh();
        tableModel.refresh();
    }

    @Override
    public RefreshableListModel<T> getListModel() {
        return listModel;
    }

    @Override
    public TableModel<T> getTableModel() {
        return tableModel;
    }

    @Override
    public TablePanel<T> getTablePanel() {
        return tablePanel;
    }

    @Override
    public ButtonTabStrategy getButtonTabStrategy() {
        return buttonTabStrategy;
    }
}
