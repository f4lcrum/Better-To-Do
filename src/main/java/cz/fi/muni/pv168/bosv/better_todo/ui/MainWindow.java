package cz.fi.muni.pv168.bosv.better_todo.ui;

import cz.fi.muni.pv168.bosv.better_todo.data.TestDataGenerator;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.action.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.components.FilterComboboxBuilder;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.components.FilterListModelBuilder;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterDurationValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.CategoryTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.*;
import cz.fi.muni.pv168.bosv.better_todo.util.Either;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.StatisticsPanel;
import org.jfree.chart.plot.PlotRenderingInfo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class MainWindow {

    private final int START_DATE_COL = 2;
    private final JFrame frame;
    private final JTable eventTable;
    private final JTable templateTable;
    private final Action addAction;
    private final Action quitAction;
    private final Action editAction;
    private final Action deleteAction;
    private final Action addTemplateAction;
    private final Action editTemplateAction;
    private final Action deleteTemplateAction;
    private final Action addCategoryAction;
    private final Action editCategoryAction;
    private final Action deleteCategoryAction;

    private final Action exportAction;
    private final Action importAction;
    private final StatusListModel statusListModel;
    private final CategoryListModel categoryListModel;
    private final JPanel statistics;

    public MainWindow() {
        var testDataGenerator = new TestDataGenerator();
        var eventTableModel = new TodoTableModel(testDataGenerator.createTestEvents(10));
        var eventTablePanel = new EventTablePanel(eventTableModel);
        var templateTableModel = new TemplateTableModel(testDataGenerator.createTestTemplates(10));
        var templateTablePanel = new TemplateTablePanel(templateTableModel);
        var categoryTableModel = new CategoryTableModel(testDataGenerator.createTestCategories(10));
        var categoryTablePanel = new CategoryTablePanel(categoryTableModel);
        var events = testDataGenerator.createTestEvents(10);
        categoryListModel = new CategoryListModel(testDataGenerator.getCategories());
        statusListModel = new StatusListModel();
        eventTable = createEventTable(events);
        templateTable = createTemplateTable(testDataGenerator.createTestTemplates(10));

        addAction = new AddEventAction(eventTablePanel.getEventTable(), categoryListModel, statusListModel);
        quitAction = new QuitAction();
        editAction = new EditEventAction(eventTablePanel.getEventTable(), categoryListModel, statusListModel);
        deleteAction = new DeleteEventAction(eventTablePanel.getEventTable());

        addCategoryAction = new AddCategoryAction(categoryTablePanel.getEventTable());
        editCategoryAction = new EditCategoryAction(categoryTablePanel.getEventTable());
        deleteCategoryAction = new DeleteCategoryAction(eventTablePanel.getEventTable());

        addTemplateAction = new AddTemplateAction(templateTablePanel.getEventTable(), categoryListModel, statusListModel);
        editTemplateAction = new EditTemplateAction(templateTablePanel.getEventTable(), categoryListModel, statusListModel);
        deleteTemplateAction = new DeleteTemplateAction(templateTablePanel.getEventTable());

        exportAction = new ExportAction(eventTablePanel);
        importAction = new ImportAction(eventTablePanel);
        statistics = new StatisticsPanel();
        frame = createFrame();

        var rowSorter = new TableRowSorter<>(eventTableModel);
        rowSorter.toggleSortOrder(START_DATE_COL); // 2 == 3rd column is start date, automatically sorts 3rd column
        var eventTableFilter = new EventTableFilter(rowSorter);
        eventTablePanel.getEventTable().setRowSorter(rowSorter);

        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Events", eventTablePanel);
        tabbedPane.addTab("Templates", templateTablePanel);
        tabbedPane.addTab("Categories", categoryTablePanel);

        // Filters
        var statusFilter = createFilterPanel(createStatusFilter(eventTableFilter, statusListModel), "Status: ");
        var durationFilter = createFilterPanel(createDurationFilter(eventTableFilter), "Duration: ");
        var categoryFilter = createFilterPanel(createCategoryFilter(eventTableFilter, categoryListModel), "Category: ");


        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.add(createToolbar(statusFilter, durationFilter, categoryFilter), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(statistics, BorderLayout.SOUTH);
        frame.add(createToolbar(statusFilter, categoryFilter, durationFilter), BorderLayout.BEFORE_FIRST_LINE);
    }

    private JTable createEventTable(List<Event> employees) {
        var model = new TodoTableModel(employees);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    private JTable createTemplateTable(List<Template> templates) {
        var model = new TemplateTableModel(templates);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        return table;
    }

    public void show() {
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var fileMenu = new JMenu("File");
        fileMenu.add(importAction);
        fileMenu.add(exportAction);
        fileMenu.add(quitAction);

        var eventMenu = new JMenu("Event");
        eventMenu.add(addAction);
        eventMenu.add(editAction);
        eventMenu.add(deleteAction);

        var categoryMenu = new JMenu("Category");
        categoryMenu.add(addCategoryAction);
        categoryMenu.add(editCategoryAction);
        categoryMenu.add(deleteCategoryAction);

        var templateMenu = new JMenu("Template");
        templateMenu.add(addTemplateAction);
        templateMenu.add(editTemplateAction);
        templateMenu.add(deleteTemplateAction);

        // var statisticsMenu = new JMenu("Statistics");

        menuBar.add(fileMenu);
        menuBar.add(eventMenu);
        menuBar.add(categoryMenu);
        menuBar.add(templateMenu);
        // menuBar.add(statisticsMenu);
        return menuBar;
    }

    private JToolBar createToolbar(Component... components) {
        var toolbar = new JToolBar();

        for (var component : components) {
            toolbar.add(component);
            toolbar.addSeparator(new Dimension(50,10));
        }
        return toolbar;
    }

    private JFrame createFrame() {
        var frame = new JFrame("TO-DO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private static JPanel createFilterPanel( Component filter, String label) {
        JPanel statusPanel = new JPanel(new BorderLayout());
        JLabel filterLabel = new JLabel(label);
        statusPanel.add(filterLabel, BorderLayout.NORTH);
        statusPanel.add(filter, BorderLayout.SOUTH);
        return statusPanel;
    }
    private static JComboBox<Either<SpecialFilterDurationValues, EventDuration>> createDurationFilter(
            EventTableFilter eventTableFilter) {
        return FilterComboboxBuilder.create(SpecialFilterDurationValues.class, EventDuration.values())
                .setSelectedItem(SpecialFilterDurationValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterDurationValuesRenderer())
                .setValuesRenderer(new DurationRenderer())
                .setFilter(eventTableFilter::filterDuration)
                .build();
    }

    private static JList<Either<SpecialFilterStatusValues, Status>> createStatusFilter(
            EventTableFilter eventTableFilter, StatusListModel statusListModel) {
        return FilterListModelBuilder.create(SpecialFilterStatusValues.class, statusListModel, "Status")
                .setSelectedIndex(0)
                .setVisibleRowsCount(3)
                .setSpecialValuesRenderer(new SpecialFilterStatusRenderer())
                .setValuesRenderer(new StatusRenderer())
                .setFilter(eventTableFilter::filterStatus)
                .build();
    }

    private static JList<Either<SpecialFilterCategoryValues, Category>> createCategoryFilter(
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
