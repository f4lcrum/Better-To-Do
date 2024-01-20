package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.business.entity.Entity;
import cz.fi.muni.pv168.todo.ui.action.strategy.ButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.model.RefreshableListModel;
import cz.fi.muni.pv168.todo.ui.model.TableModel;
import cz.fi.muni.pv168.todo.ui.panels.TablePanel;

public interface MainWindowEntity<T extends Entity> {
    void refreshModel();

    RefreshableListModel<T> getListModel();

    TableModel<T> getTableModel();

    TablePanel<T> getTablePanel();

    ButtonTabStrategy getButtonTabStrategy();
}
