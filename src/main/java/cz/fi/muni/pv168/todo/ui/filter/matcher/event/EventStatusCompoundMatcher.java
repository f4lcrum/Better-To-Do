package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

import java.util.Collection;

public class EventStatusCompoundMatcher extends EntityMatcher<Event> {

    private final Collection<EntityMatcher<Event>> employeeMatchers;

    public EventStatusCompoundMatcher(Collection<EntityMatcher<Event>> employeeMatchers) {
        this.employeeMatchers = employeeMatchers;
    }

    @Override
    public boolean evaluate(Event event) {
        return employeeMatchers.stream()
                .anyMatch(matcher -> matcher.evaluate(event));
    }
}
