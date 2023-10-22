package cz.fi.muni.pv168.bosv.better_todo.ui;

import cz.fi.muni.pv168.bosv.better_todo.entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.entity.Template;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TemplateTableModel;
import cz.fi.muni.pv168.bosv.better_todo.data.TestDataGenerator;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.StatisticsPanel;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.JButton;

public class MainWindow {

    private final JFrame frame;
    private final JTable eventTable;
    private final JTable templateTable;
    private final JPanel statistics;

    public MainWindow() {
        var testDataGenerator = new TestDataGenerator();

        eventTable = createEventTable(testDataGenerator.createTestEvents(10));
        templateTable = createTemplateTable(testDataGenerator.createTestTemplates(10));
        statistics = new StatisticsPanel();
        frame = createFrame();
        frame.setJMenuBar(createMenuBar());
        frame.add(createToolbar());

        var eventTablePanel = new JScrollPane(eventTable);
        var templateTablePanel = new JScrollPane(templateTable);
        var statisticsPanel = new JScrollPane(statistics);

        var tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Events", eventTablePanel);
        tabbedPane.addTab("Templates", templateTablePanel);
        tabbedPane.addTab("Statistics", statisticsPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.pack();
        frame.add(createToolbar(), BorderLayout.BEFORE_FIRST_LINE);
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
        var eventMenu = new JMenu("Event");
        var categoryMenu = new JMenu("Category");
        var templateMenu = new JMenu("Template");
        var statisticsMenu = new JMenu("Statistics");

        menuBar.add(fileMenu);
        menuBar.add(eventMenu);
        menuBar.add(categoryMenu);
        menuBar.add(templateMenu);
        menuBar.add(statisticsMenu);
        return menuBar;
    }

    private JToolBar createToolbar() {
        var toolbar = new JToolBar();

        var button = new JButton("Events");
        button.setPreferredSize(new Dimension(50, 50));
        // TODO: change view: button.setActionCommand();
        toolbar.add(button);
        toolbar.addSeparator();
        return toolbar;
    }

    private JFrame createFrame() {
        var frame = new JFrame("TO-DO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

}
