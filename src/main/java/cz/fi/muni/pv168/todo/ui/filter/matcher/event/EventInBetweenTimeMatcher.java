package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

import java.time.LocalTime;

public class EventInBetweenTimeMatcher extends EntityMatcher<Event> {

    private final LocalTime fromTime;
    private final LocalTime toTime;

    public EventInBetweenTimeMatcher(LocalTime fromTime, LocalTime toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public boolean evaluate(Event event) {
        return this.fromTime.isBefore(event.getStartTime()) &&
                this.toTime.isAfter(event.getEndTime());
    }

}
