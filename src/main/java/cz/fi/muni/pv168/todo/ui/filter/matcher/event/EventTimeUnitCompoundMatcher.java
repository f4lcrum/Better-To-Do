package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

import java.util.Collection;

public class EventTimeUnitCompoundMatcher extends EntityMatcher<Event> {

    private final Collection<EntityMatcher<Event>> eventMatcher;

    public EventTimeUnitCompoundMatcher(Collection<EntityMatcher<Event>> eventMatcher) {
        this.eventMatcher = eventMatcher;
    }

    @Override
    public boolean evaluate(Event event) {
        return eventMatcher.stream()
                .anyMatch(matcher -> matcher.evaluate(event));
    }
}
