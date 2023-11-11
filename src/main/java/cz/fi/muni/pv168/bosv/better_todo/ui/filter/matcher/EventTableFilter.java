package cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher;


import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.EventDuration;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.event.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterDurationValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.util.Either;

import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class holding all filters for the EventTable.
 *
 * @author Vojtěch Sassmann, BOSV team
 */
public final class EventTableFilter {
    private final EventCompoundMatcher employeeCompoundMatcher;

    public EventTableFilter(TableRowSorter<TodoTableModel> rowSorter) {
        employeeCompoundMatcher = new EventCompoundMatcher(rowSorter);
        rowSorter.setRowFilter(employeeCompoundMatcher);
    }

    public void filterDate(LocalDate date) {
        employeeCompoundMatcher.setDateMatcher(new EventDateMatcher(date));
    }

    public void filterBetweenDate(LocalTime from, LocalTime to) {
        employeeCompoundMatcher.setInBetweenTimeMatcher(new EventInBetweenTimeMatcher(from, to));
    }

    public void filterStatus(List<Either<SpecialFilterStatusValues, Status>> selectedItems) {
        List<EntityMatcher<Event>> matchers = new ArrayList<>();
        selectedItems.forEach(either -> either.apply(
                l -> matchers.add(l.getMatcher()),
                r -> matchers.add(new EventStatusMatcher(r))
        ));
        employeeCompoundMatcher.setStatusMatcher(new EventStatusCompoundMatcher(matchers));
    }

    public void filterDuration(Either<SpecialFilterDurationValues, EventDuration> selectedItems) {
        selectedItems.apply(
                l -> employeeCompoundMatcher.setIntervalMatcher(l.getMatcher()),
                r -> employeeCompoundMatcher.setIntervalMatcher(new EventIntervalMatcher(r))
        );
    }

    public void filterCategory(List<Either<SpecialFilterCategoryValues, Category>> selectedItems) {
        List<EntityMatcher<Event>> matchers = new ArrayList<>();
        selectedItems.forEach(either -> either.apply(
                l -> matchers.add(l.getMatcher()),
                r -> matchers.add(new EventCategoryMatcher(r))
        ));
        employeeCompoundMatcher.setCategoryMatcher(new EventCategoryCompoundMatcher(matchers));
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
        private EntityMatcher<Event> inBetweenDateMatcher = EntityMatchers.all();
        private EntityMatcher<Event> statusMatcher = EntityMatchers.all();
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

        private void setInBetweenTimeMatcher(EntityMatcher<Event> inBetweenDateMatcher) {
            this.inBetweenDateMatcher = inBetweenDateMatcher;
            rowSorter.sort();
        }

        private void setStatusMatcher(EntityMatcher<Event> statusMatcher) {
            this.statusMatcher = statusMatcher;
            rowSorter.sort();
        }

        private void setCategoryMatcher(EntityMatcher<Event> categoryMatcher) {
            this.categoryMatcher = categoryMatcher;
            rowSorter.sort();
        }

        @Override
        public boolean evaluate(Event event) {
            return Stream.of(dateMatcher, intervalMatcher, inBetweenDateMatcher, statusMatcher, categoryMatcher).allMatch(m -> m.evaluate(event));
        }
    }
}
