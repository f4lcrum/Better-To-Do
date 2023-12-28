package cz.fi.muni.pv168.todo.ui.filter.components;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Status;
import cz.fi.muni.pv168.todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.todo.ui.renderer.CategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.SpecialFilterCategoryRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.SpecialFilterStatusRenderer;
import cz.fi.muni.pv168.todo.ui.renderer.StatusRenderer;
import cz.fi.muni.pv168.todo.util.Either;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;


public class FilterPanel<L extends Enum<L>, R> {

    public static JPanel createFilterPanel(Component filter, String label) {
        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel filterLabel = new JLabel(label);
        statusPanel.add(filterLabel, BorderLayout.NORTH);
        statusPanel.add(filter, BorderLayout.SOUTH);
        return statusPanel;
    }

    public static JList<Either<SpecialFilterStatusValues, Status>> createStatusFilter(
            EventTableFilter eventTableFilter, StatusListModel statusListModel) {
        return FilterListModelBuilder.create(SpecialFilterStatusValues.class, statusListModel, "Status")
                .setSelectedIndex(0)
                .setVisibleRowsCount(3)
                .setSpecialValuesRenderer(new SpecialFilterStatusRenderer())
                .setValuesRenderer(new StatusRenderer())
                .setFilter(eventTableFilter::filterStatus)
                .build();
    }

    public static JList<Either<SpecialFilterCategoryValues, Category>> createCategoryFilter(
            EventTableFilter eventTableFilter, CategoryListModel categoryListModel) {
        return FilterListModelBuilder.create(SpecialFilterCategoryValues.class, categoryListModel, "Category")
                .setSelectedIndex(0)
                .setVisibleRowsCount(3)
                .setSpecialValuesRenderer(new SpecialFilterCategoryRenderer())
                .setValuesRenderer(new CategoryRenderer())
                .setFilter(eventTableFilter::filterCategory)
                .build();
    }
}
