package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

import java.time.LocalDate;

public class EventDateMatcher extends EntityMatcher<Event> {

    private final LocalDate date;

    @Override
    public boolean evaluate(Event event) {
        return this.date.equals(event.getDate());
    }

    public EventDateMatcher(LocalDate date) {
        this.date = date;
    }
}
