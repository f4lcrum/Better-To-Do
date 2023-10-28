package cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EntityMatcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@AllArgsConstructor
@NonNull
public class EventCategoryMatcher  extends EntityMatcher<Event> {
    private final Category category;

    @Override
    public boolean evaluate(Event event) {
        return this.category.equals(event.getCategory());
    }
}
