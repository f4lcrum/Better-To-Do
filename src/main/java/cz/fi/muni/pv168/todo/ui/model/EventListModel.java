package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import java.util.ArrayList;
import java.util.List;

public class EventListModel extends RefreshableListModel<Event> {

    private List<Event> events;
    private final CrudService<Event> eventCrudService;

    public EventListModel(CrudService<Event> eventCrudService) {
        this.eventCrudService = eventCrudService;
        this.events = new ArrayList<>(eventCrudService.findAll());
    }

    @Override
    public int getSize() {
        return events.size();
    }

    @Override
    public Event getElementAt(int index) {
        return events.get(index);
    }

    @Override
    public void refresh() {
        this.events = new ArrayList<>(eventCrudService.findAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }
}
