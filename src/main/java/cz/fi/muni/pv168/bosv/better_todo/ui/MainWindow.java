package cz.fi.muni.pv168.bosv.better_todo.ui;

import cz.fi.muni.pv168.bosv.better_todo.data.TestDataGenerator;
import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.action.*;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.components.FilterComboboxBuilder;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.matcher.EventTableFilter;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterCategoryValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterDurationValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.filter.values.SpecialFilterStatusValues;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.CategoryListModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.StatusListModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.TemplateTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.ui.renderer.*;
import cz.fi.muni.pv168.bosv.better_todo.util.Either;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.StatisticsPanel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class MainWindow {

    private final JFrame frame;
    private final JTable eventTable;
    private final JTable templateTable;
    private final Action addAction;
    private final Action quitAction;
    private final Action editAction;
    private final Action deleteAction;
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
        var events = testDataGenerator.createTestEvents(10);
        categoryListModel = new CategoryListModel(testDataGenerator.getCategories());
        statusListModel = new StatusListModel();
        eventTable = createEventTable(events);
        templateTable = createTemplateTable(testDataGenerator.createTestTemplates(10));
        addAction = new AddAction(eventTablePanel.getEventTable(), categoryListModel, statusListModel);
        quitAction = new QuitAction();
        editAction = new EditAction(eventTablePanel.getEventTable(), categoryListModel, statusListModel);
        deleteAction = new DeleteAction(eventTablePanel.getEventTable());
        exportAction = new ExportAction(eventTablePanel);
        importAction = new ImportAction(eventTablePanel);
        statistics = new StatisticsPanel();
        frame = createFrame();

        var rowSorter = new TableRowSorter<>(eventTableModel);
        var eventTableFilter = new EventTableFilter(rowSorter);
        eventTablePanel.getEventTable().setRowSorter(rowSorter);

        var statisticsPanel = new JScrollPane(statistics);

        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Events", eventTablePanel);
        tabbedPane.addTab("Templates", templateTablePanel);
        tabbedPane.addTab("Statistics", statisticsPanel);

        // Filters
        var statusFilter = createStatusFilter(eventTableFilter);
        var durationFilter = createDurationFilter(eventTableFilter);
        var categoryFilter = createCategoryFilter(eventTableFilter, categoryListModel);


        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.add(createToolbar(statusFilter, durationFilter, categoryFilter), BorderLayout.BEFORE_FIRST_LINE);
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
        categoryMenu.add(addAction);
        categoryMenu.add(editAction);
        categoryMenu.add(deleteAction);

        var templateMenu = new JMenu("Template");
        templateMenu.add(addAction);
        templateMenu.add(editAction);
        templateMenu.add(deleteAction);

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

        var button = new JButton("Events");
        button.setPreferredSize(new Dimension(50, 50));
        // TODO: change view: button.setActionCommand();
        toolbar.add(button);
        toolbar.addSeparator();

        for (var component : components) {
            toolbar.add(component);
        }
        return toolbar;
    }

    private JFrame createFrame() {
        var frame = new JFrame("TO-DO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
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

    private static JComboBox<Either<SpecialFilterStatusValues, Status>> createStatusFilter(
            EventTableFilter eventTableFilter) {
        return FilterComboboxBuilder.create(SpecialFilterStatusValues.class, Status.values())
                .setSelectedItem(SpecialFilterStatusValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterStatusRenderer())
                .setValuesRenderer(new StatusRenderer())
                .setFilter(eventTableFilter::filterStatus)
                .build();
    }

    private static JComboBox<Either<SpecialFilterCategoryValues, Category>> createCategoryFilter(
            EventTableFilter eventTableFilter, CategoryListModel categoryListModel) {
        return FilterComboboxBuilder.create(SpecialFilterCategoryValues.class, categoryListModel)
                .setSelectedItem(SpecialFilterCategoryValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterCategoryRenderer())
                .setValuesRenderer(new CategoryRenderer())
                .setFilter(eventTableFilter::filterCategory)
                .build();
    }
}
