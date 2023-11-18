package cz.fi.muni.pv168.todo.ui.filter.values;

import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatcher;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EntityMatchers;

public enum SpecialFilterCategoryValues {
    ALL(EntityMatchers.all());

    private final EntityMatcher<Event> matcher;

    SpecialFilterCategoryValues(EntityMatcher<Event> matcher) {
        this.matcher = matcher;
    }

    public EntityMatcher<Event> getMatcher() {
        return matcher;
    }
}
