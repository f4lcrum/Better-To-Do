package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import java.util.ArrayList;
import java.util.List;

public class TimeUnitListModel extends RefreshableListModel<TimeUnit> {
    private List<TimeUnit> timeUnits;
    private final CrudService<TimeUnit> timeUnitCrudService;

    public TimeUnitListModel(CrudService<TimeUnit> timeUnitCrudService) {
        this.timeUnitCrudService = timeUnitCrudService;
        this.timeUnits = new ArrayList<>(timeUnitCrudService.findAll());
    }

    @Override
    public int getSize() {
        return timeUnits.size();
    }

    @Override
    public TimeUnit getElementAt(int index) {
        return timeUnits.get(index);
    }

    @Override
    public void refresh() {
        this.timeUnits = new ArrayList<>(timeUnitCrudService.findAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }
}
