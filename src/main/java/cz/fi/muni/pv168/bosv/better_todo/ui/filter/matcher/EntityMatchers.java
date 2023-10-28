package cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher;

/**
 * Class with static methods providing generic entity matchers.
 *
 * @author Vojtěch Sassmann
 */
public class EntityMatchers {
    private EntityMatchers() {
    }

    /**
     * Creates new instance of {@link EntityMatcher} which results to true by any
     * given instance of the entity type {@link T}.
     *
     * @param <T> type for the created entity matcher
     * @return created entity matcher
     */
    public static <T> EntityMatcher<T> all() {
        return new EntityMatcher<>() {
            @Override
            public boolean evaluate(T entity) {
                return true;
            }
        };
    }
}

