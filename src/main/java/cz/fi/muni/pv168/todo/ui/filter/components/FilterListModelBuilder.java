package cz.fi.muni.pv168.todo.ui.filter.components;

import cz.fi.muni.pv168.todo.ui.model.CustomValuesModelDecorator;
import cz.fi.muni.pv168.todo.ui.renderer.AbstractRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.EitherRenderer;
import cz.fi.muni.pv168.todo.util.Either;

import javax.swing.JList;
import javax.swing.ListModel;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Vojtěch Sassmann
 */

public class FilterListModelBuilder<L extends Enum<L>, R> {

    private final Class<L> clazz;
    private final ListModel<R> values;
    private final String name;
    private AbstractRenderer<L> specialValuesRenderer;
    private AbstractRenderer<R> valuesRenderer;
    private Consumer<List<Either<L, R>>> filter;
    private int selectedIndex = 0;
    private int visibleRowsCount = 3;

    private FilterListModelBuilder(Class<L> clazz, ListModel<R> values, String name) {
        this.clazz = clazz;
        this.values = values;
        this.name = name;
    }

    public static <L extends Enum<L>, R> FilterListModelBuilder<L, R> create(Class<L> clazz, ListModel<R> values, String name) {
        return new FilterListModelBuilder<>(clazz, values, name);
    }

    public JList<Either<L, R>> build() {
        var jList = new JList<>(CustomValuesModelDecorator.addCustomValues(clazz, values));
        jList.setCellRenderer(EitherRenderer.create(specialValuesRenderer, valuesRenderer));
        jList.setSelectedIndex(selectedIndex);
        jList.setVisibleRowCount(visibleRowsCount);
        jList.addListSelectionListener(e -> filter.accept(jList.getSelectedValuesList()));

        return jList;
    }

    public FilterListModelBuilder<L, R> setSpecialValuesRenderer(AbstractRenderer<L> specialValuesRenderer) {
        this.specialValuesRenderer = specialValuesRenderer;
        return this;
    }

    public FilterListModelBuilder<L, R> setValuesRenderer(AbstractRenderer<R> valuesRenderer) {
        this.valuesRenderer = valuesRenderer;
        return this;
    }

    public FilterListModelBuilder<L, R> setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        return this;
    }

    public FilterListModelBuilder<L, R> setVisibleRowsCount(int visibleRowsCount) {
        this.visibleRowsCount = visibleRowsCount;
        return this;
    }

    public FilterListModelBuilder<L, R> setFilter(Consumer<List<Either<L, R>>> filter) {
        this.filter = filter;
        return this;
    }
}
