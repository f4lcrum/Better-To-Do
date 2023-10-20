package cz.muni.fi.pv168.Entity.ui;

import cz.fi.muni.pv168.bosv.better_todo.Entity.Event;
import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.data.TestDataGenerator;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow {

    private final JFrame frame;
    private final JTable table;

//    private final TodoTableModel todoTableModel;

    public MainWindow() {
        var testDataGenerator = new TestDataGenerator();

        table = createEventTable(testDataGenerator.createTestEvents(10));
        frame = createFrame();
        frame.setJMenuBar(createMenuBar());
        frame.add(createToolbar());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
//        todoTableModel = new TodoTableModel();
//        var eventTablePanel = new EventTablePanel(todoTableModel);
//        frame.add(eventTablePanel);

        frame.pack();
    }

    private JTable createEventTable(List<Event> employees) {
        var model = new TodoTableModel(employees);
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
        return toolbar;
    }

    private JFrame createFrame() {
        var frame = new JFrame("TO-DO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

}
