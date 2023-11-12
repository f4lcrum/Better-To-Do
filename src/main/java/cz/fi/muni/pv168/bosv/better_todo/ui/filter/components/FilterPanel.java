package cz.fi.muni.pv168.bosv.better_todo.ui.filter.components;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;
import cz.fi.muni.pv168.bosv.better_todo.entity.EventDuration;
import cz.fi.muni.pv168.bosv.better_todo.entity.Status;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.TemplateTableFilter;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterDurationValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.*;
import cz.fi.muni.pv168.bosv.better_todo.util.Either;

import javax.swing.*;
import java.awt.*;


public class FilterPanel<L extends Enum<L>, R> {

    private Component filter;
    private String label;

    public static void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }

    public static JPanel createFilterPanel( Component filter, String label) {
        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel filterLabel = new JLabel(label);
        statusPanel.add(filterLabel, BorderLayout.NORTH);
        statusPanel.add(filter, BorderLayout.SOUTH);
        return statusPanel;
    }

    public static JComboBox<Either<SpecialFilterDurationValues, EventDuration>> createDurationFilter(
            TemplateTableFilter templateTableFilter) {
        return FilterComboboxBuilder.create(SpecialFilterDurationValues.class, EventDuration.values())
                .setSelectedItem(SpecialFilterDurationValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterDurationValuesRenderer())
                .setValuesRenderer(new DurationRenderer())
                .setFilter(templateTableFilter::filterDuration)
                .build();
    }
    public static JComboBox<Either<SpecialFilterDurationValues, EventDuration>> createDurationFilter(
            EventTableFilter eventTableFilter) {
        return FilterComboboxBuilder.create(SpecialFilterDurationValues.class, EventDuration.values())
                .setSelectedItem(SpecialFilterDurationValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterDurationValuesRenderer())
                .setValuesRenderer(new DurationRenderer())
                .setFilter(eventTableFilter::filterDuration)
                .build();
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
