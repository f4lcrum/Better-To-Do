package cz.fi.muni.pv168.todo.ui.model;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;


abstract class Column<E, T> {

    private final String name;
    private final Class<T> columnType;
    private final Function<E, T> valueGetter;

    private Column(String name, Class<T> columnType, Function<E, T> valueGetter) {
        this.name = name;
        this.columnType = columnType;
        this.valueGetter = valueGetter;
    }

    public static <E, T> Column<E, T> editable(String name, Class<T> columnClass,
                                               Function<E, T> valueGetter,
                                               BiConsumer<E, T> valueSetter) {
        return new Editable<>(name, columnClass, valueGetter, valueSetter);
    }

    public static <E, T> Column<E, T> readonly(String name,
                                               Class<T> columnClass,
                                               Function<E, T> valueGetter) {
        return new ReadOnly<>(name, columnClass, valueGetter);
    }

    abstract boolean isEditable();

    public String getName() {
        return name;
    }

    abstract void setValue(Object value, E entity);

    T getValue(E entity) {
        return valueGetter.apply(entity);
    }


    Class<T> getColumnType() {
        return columnType;
    }

    private static class ReadOnly<E, T> extends Column<E, T> {

        private ReadOnly(String name, Class<T> columnClass, Function<E, T> valueGetter) {
            super(name, columnClass, valueGetter);
        }

        @Override
        boolean isEditable() {
            return false;
        }

        @Override
        void setValue(Object value, E entity) {
            throw new UnsupportedOperationException("Column '" + getName() + "' is not editable");
        }
    }

    private static class Editable<E, T> extends Column<E, T> {

        private final BiConsumer<E, T> valueSetter;

        private Editable(String name, Class<T> columnClass, Function<E, T> valueGetter, BiConsumer<E, T> valueSetter) {
            super(name, columnClass, valueGetter);
            this.valueSetter = Objects.requireNonNull(valueSetter, "value setter cannot be null");
        }

        @Override
        boolean isEditable() {
            return true;
        }

        @Override
        void setValue(Object value, E entity) {
            valueSetter.accept(entity, getColumnType().cast(value));
        }
    }
}
