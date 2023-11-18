package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.EventDuration;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

public class EventIntervalMatcher extends EntityMatcher<Event> {
    private final EventDuration eventDuration;

    public EventIntervalMatcher(EventDuration eventDuration) {
        this.eventDuration = eventDuration;
    }

    @Override
    public boolean evaluate(Event event) {
        return event.getEventDuration() <= eventDuration.getDuration();
    }
}