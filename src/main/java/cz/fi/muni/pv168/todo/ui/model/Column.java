package cz.fi.muni.pv168.todo.ui.model;

import java.util.function.BiConsumer;
import java.util.function.Function;

class Column<E, T> {

    private final String name;
    private final Class<T> columnType;
    private final Function<E, T> valueGetter;

    public Column(String name, Class<T> columnType, Function<E, T> valueGetter) {
        this.name = name;
        this.columnType = columnType;
        this.valueGetter = valueGetter;
    }

    public String getName() {
        return name;
    }

    T getValue(E entity) {
        return valueGetter.apply(entity);
    }

    Class<T> getColumnType() {
        return columnType;
    }
}
