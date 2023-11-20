package cz.fi.muni.pv168.todo.ui.filter.matcher;


import cz.fi.muni.pv168.todo.entity.Category;
import cz.fi.muni.pv168.todo.entity.Event;
import cz.fi.muni.pv168.todo.entity.Status;
import cz.fi.muni.pv168.todo.ui.filter.matcher.event.EventCategoryCompoundMatcher;
import cz.fi.muni.pv168.todo.ui.filter.matcher.event.EventCategoryMatcher;
import cz.fi.muni.pv168.todo.ui.filter.matcher.event.EventStatusCompoundMatcher;
import cz.fi.muni.pv168.todo.ui.filter.matcher.event.EventStatusMatcher;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.todo.ui.model.EventTableModel;
import cz.fi.muni.pv168.todo.util.Either;

import javax.swing.table.TableRowSorter;
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

    public EventTableFilter(TableRowSorter<EventTableModel> rowSorter) {
        employeeCompoundMatcher = new EventCompoundMatcher(rowSorter);
        rowSorter.setRowFilter(employeeCompoundMatcher);
    }

    public void filterStatus(List<Either<SpecialFilterStatusValues, Status>> selectedItems) {
        List<EntityMatcher<Event>> matchers = new ArrayList<>();
        selectedItems.forEach(either -> either.apply(
                l -> matchers.add(l.getMatcher()),
                r -> matchers.add(new EventStatusMatcher(r))
        ));
        employeeCompoundMatcher.setStatusMatcher(new EventStatusCompoundMatcher(matchers));
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

        private final TableRowSorter<EventTableModel> rowSorter;
        private EntityMatcher<Event> statusMatcher = EntityMatchers.all();
        private EntityMatcher<Event> categoryMatcher = EntityMatchers.all();

        private EventCompoundMatcher(TableRowSorter<EventTableModel> rowSorter) {
            this.rowSorter = rowSorter;
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
            return Stream.of(statusMatcher, categoryMatcher).allMatch(m -> m.evaluate(event));
        }
    }
}
