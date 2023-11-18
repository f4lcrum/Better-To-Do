package cz.fi.muni.pv168.todo.ui.filter.matcher.event;

import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;

public class EventCategoryMatcher extends EntityMatcher<Event> {
    private final Category category;

    @Override
    public boolean evaluate(Event event) {
        return this.category.equals(event.getCategory());
    }

    public EventCategoryMatcher(Category category) {
        this.category = category;
    }
}
