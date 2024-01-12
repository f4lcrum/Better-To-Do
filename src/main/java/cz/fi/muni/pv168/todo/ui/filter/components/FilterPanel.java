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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JTextField;


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

    public static DurationFilterComponents createDurationFilter(EventTableFilter eventTableFilter) {
        JPanel durationPanel = new JPanel();
        JLabel minLabel = new JLabel("Min Duration:");
        JTextField minDurationField = new JTextField(5);
        JLabel maxLabel = new JLabel("Max Duration:");
        JTextField maxDurationField = new JTextField(5);
        JButton applyFilterButton = new JButton("Apply");

        applyFilterButton.addActionListener(e -> {
            try {
                Integer minDuration = minDurationField.getText().isEmpty() ? null : Integer.parseInt(minDurationField.getText());
                Integer maxDuration = maxDurationField.getText().isEmpty() ? null : Integer.parseInt(maxDurationField.getText());

                if (minDuration == null && maxDuration == null) {
                    eventTableFilter.resetDurationFilter();
                } else {
                    eventTableFilter.filterDuration(minDuration, maxDuration);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(durationPanel, "Please enter valid integer values for duration.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        durationPanel.add(minLabel);
        durationPanel.add(minDurationField);
        durationPanel.add(maxLabel);
        durationPanel.add(maxDurationField);
        durationPanel.add(applyFilterButton);

        return new DurationFilterComponents(durationPanel, minDurationField, maxDurationField);
    }

    public static JButton createResetFiltersButton(EventTableFilter eventTableFilter) {
        JButton resetButton = new JButton("Reset All Filters");
        resetButton.addActionListener(e -> eventTableFilter.resetAllFilters());
        return resetButton;
    }

}
