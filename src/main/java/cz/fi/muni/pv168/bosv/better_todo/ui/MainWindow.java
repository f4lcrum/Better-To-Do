package cz.muni.fi.pv168.Entity.ui;

import cz.fi.muni.pv168.bosv.better_todo.ui.model.TodoTableModel;
import cz.fi.muni.pv168.bosv.better_todo.ui.panels.EventTablePanel;
import cz.fi.muni.pv168.bosv.better_todo.data.TestDataGenerator;


import javax.swing.*;

public class MainWindow {

    private final JFrame frame;

    private final TodoTableModel todoTableModel;

    public MainWindow() {
        var testDataGenerator = new TestDataGenerator();

        frame = createFrame();
        frame.setJMenuBar(createMenuBar());
        frame.add(createToolbar());
        todoTableModel = new TodoTableModel();
        var eventTablePanel = new EventTablePanel(todoTableModel);
        frame.add(eventTablePanel);
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
