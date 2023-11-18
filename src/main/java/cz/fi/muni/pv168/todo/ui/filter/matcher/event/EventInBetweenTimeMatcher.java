package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalTime;

@AllArgsConstructor
@NonNull
public class EventInBetweenTimeMatcher extends EntityMatcher<Event> {
    private final LocalTime fromTime;
    private final LocalTime toTime;

    @Override
    public boolean evaluate(Event event) {
        return this.fromTime.isBefore(event.getStartTime()) &&
                this.toTime.isAfter(event.getEndTime());
    }
}
