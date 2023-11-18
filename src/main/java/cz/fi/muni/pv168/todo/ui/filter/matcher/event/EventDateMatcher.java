package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@AllArgsConstructor
@NonNull
public class EventDateMatcher extends EntityMatcher<Event> {
    private final LocalDate date;

    @Override
    public boolean evaluate(Event event) {
        return this.date.equals(event.getDate());
    }
}
