package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

public class EventDurationMatcher extends EntityMatcher<Event> {
    private final int minDuration;
    private final int maxDuration;

    public EventDurationMatcher(int minDuration, int maxDuration) {
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    @Override
    public boolean evaluate(Event event) {
        long duration = event.getDuration() * (event.getTimeUnit().getHours() * 60 + event.getTimeUnit().getMinutes());
        return duration >= minDuration && duration <= maxDuration;
    }
}
