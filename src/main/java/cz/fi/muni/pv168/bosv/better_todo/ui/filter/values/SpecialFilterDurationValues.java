package cz.fi.muni.pv168.bosv.better_todo.ui.filter.values;

import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EntityMatcher;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EntityMatchers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@NonNull
@Getter
public enum SpecialFilterDurationValues {
    ALL(EntityMatchers.all());

    private final EntityMatcher<Event> matcher;
}
