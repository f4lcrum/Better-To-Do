package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

public class EventStatusMatcher extends EntityMatcher<Event> {

    private final Status status;

    public EventStatusMatcher(Status status) {
        this.status = status;
    }

    @Override
    public boolean evaluate(Event event) {
        return status == event.getStatus();
    }
}
