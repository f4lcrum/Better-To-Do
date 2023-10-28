package cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.EventDuration;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EntityMatcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NonNull
public class EventIntervalMatcher extends EntityMatcher<Event> {
    private final EventDuration eventDuration;

    @Override
    public boolean evaluate(Event event) {
        return event.getEventDuration() <= eventDuration.getDuration();
    }
}