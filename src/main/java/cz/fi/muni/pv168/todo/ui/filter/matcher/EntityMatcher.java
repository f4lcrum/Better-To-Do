package cz.fi.muni.pv168.todo.ui.filter.matcher;

import cz.fi.muni.pv168.todo.ui.model.EntityTableModel;

import javax.swing.RowFilter;

/**
 * General entity matcher which can be extended by implementing the {@link EntityMatcher#evaluate(Object)}
 * method.
 *
 * @param <T> entity type
 * @author Vojtěch Sassmann
 */
public abstract class EntityMatcher<T> extends RowFilter<EntityTableModel<T>, Integer> {

    @Override
    public boolean include(Entry<? extends EntityTableModel<T>, ? extends Integer> entry) {
        EntityTableModel<T> tableModel = entry.getModel();
        int rowIndex = entry.getIdentifier();
        T entity = tableModel.getEntity(rowIndex);

        return evaluate(entity);
    }

    public abstract boolean evaluate(T entity);
}

