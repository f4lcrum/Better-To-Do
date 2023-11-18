package cz.fi.muni.pv168.todo.ui.filter.matcher;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.EventDuration;
import cz.fi.muni.pv168.todo.ui.filter.matcher.event.EventCategoryMatcher;
import cz.fi.muni.pv168.todo.ui.filter.matcher.event.EventIntervalMatcher;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterDurationValues;
import cz.fi.muni.pv168.todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.todo.util.Either;
import cz.fi.muni.pv168.todo.ui.filter.matcher.event.EventCategoryCompoundMatcher;

import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class holding all filters for the EventTable.
 *
 * @author Vojtěch Sassmann, BOSV team
 */
public final class TemplateTableFilter {
    private final EventCompoundMatcher templateCompoundMatcher;

    public TemplateTableFilter(TableRowSorter<TodoTableModel> rowSorter) {
        templateCompoundMatcher = new EventCompoundMatcher(rowSorter);
        rowSorter.setRowFilter(templateCompoundMatcher);
    }
    public void filterDuration(Either<SpecialFilterDurationValues, EventDuration> selectedItems) {
        selectedItems.apply(
                l -> templateCompoundMatcher.setIntervalMatcher(l.getMatcher()),
                r -> templateCompoundMatcher.setIntervalMatcher(new EventIntervalMatcher(r))
        );
    }

    public void filterCategory(List<Either<SpecialFilterCategoryValues, Category>> selectedItems) {
        List<EntityMatcher<Event>> matchers = new ArrayList<>();
        selectedItems.forEach(either -> either.apply(
                l -> matchers.add(l.getMatcher()),
                r -> matchers.add(new EventCategoryMatcher(r))
        ));
        templateCompoundMatcher.setCategoryMatcher(new EventCategoryCompoundMatcher(matchers));
    }

    /**
     * Container class for all matchers for the EmployeeTable.
     * <p>
     * This Matcher evaluates to true, if all contained {@link EntityMatcher} instances
     * evaluate to true.
     *
     * @author Vojtěch Sassmann, BOSV team
     */
    private static class EventCompoundMatcher extends EntityMatcher<Event> {

        private final TableRowSorter<TodoTableModel> rowSorter;
        private EntityMatcher<Event> dateMatcher = EntityMatchers.all();
        private EntityMatcher<Event> intervalMatcher = EntityMatchers.all();
        private EntityMatcher<Event> categoryMatcher = EntityMatchers.all();

        private EventCompoundMatcher(TableRowSorter<TodoTableModel> rowSorter) {
            this.rowSorter = rowSorter;
        }

        private void setDateMatcher(EntityMatcher<Event> dateMatcher) {
            this.dateMatcher = dateMatcher;
            rowSorter.sort();
        }

        private void setIntervalMatcher(EntityMatcher<Event> intervalMatcher) {
            this.intervalMatcher = intervalMatcher;
            rowSorter.sort();
        }


        private void setCategoryMatcher(EntityMatcher<Event> categoryMatcher) {
            this.categoryMatcher = categoryMatcher;
            rowSorter.sort();
        }

        @Override
        public boolean evaluate(Event event) {
            return Stream.of(dateMatcher, intervalMatcher, categoryMatcher).allMatch(m -> m.evaluate(event));
        }
    }
}
