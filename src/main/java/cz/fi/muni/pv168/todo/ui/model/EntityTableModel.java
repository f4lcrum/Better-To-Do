package cz.fi.muni.pv168.todo.ui.model;

import javax.swing.table.TableModel;

/**
 * The {@link EntityTableModel} interface provides an ability to get the actual entity at a certain index.
 *
 * @param <E> The entity type which the table keeps in a list.
 */
public interface EntityTableModel<E> extends TableModel {

    /**
     * Gets the entity at a certain index.
     *
     * @param rowIndex The index of the requested entity
     * @throws IndexOutOfBoundsException in case the rowIndex is less than zero or greater or equal
     *                                   than number of items in the table
     * */
    E getEntity(int rowIndex);
}
