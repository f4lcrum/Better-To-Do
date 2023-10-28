package cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EntityMatcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NonNull
public class EventStatusMatcher extends EntityMatcher<Event> {
    private final Status status;

    @Override
    public boolean evaluate(Event event) {
        return status == event.getStatus();
    }
}
