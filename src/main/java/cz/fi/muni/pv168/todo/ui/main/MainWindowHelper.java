package cz.fi.muni.pv168.todo.ui.main;

import cz.fi.muni.pv168.todo.ui.action.strategy.ButtonTabStrategy;
import cz.fi.muni.pv168.todo.ui.listener.PanelChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Dimension;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class MainWindowHelper {

    private static JMenu createMenu(String title, ButtonTabStrategy strategy) {
        var menu = new JMenu(title);
        menu.add(strategy.getAddAction());
        menu.add(strategy.getEditAction());
        menu.add(strategy.getDeleteAction());
        return menu;
    }

    private static JMenu createMenu(String title, Action... actions) {
        var menu = new JMenu(title);
        for (Action action : actions) {
            menu.add(action);
        }
        return menu;
    }

    public static JMenuBar createMenuBar(PanelChangeListener listener, ButtonTabStrategy eventButtonTabStrategy, ButtonTabStrategy templateButtonTabStrategy,
                                         ButtonTabStrategy categoryButtonTabStrategy, ButtonTabStrategy timeUnitButtonTabStrategy, Action... actions) {
        var menuBar = new JMenuBar();
        var fileMenu = createMenu("File", actions);
        var eventMenu = createMenu("Event", eventButtonTabStrategy);
        var templateMenu = createMenu("Template", templateButtonTabStrategy);
        var categoryMenu = createMenu("Category", categoryButtonTabStrategy);
        var timeUnitMenu = createMenu("Time Unit", timeUnitButtonTabStrategy);

        listener.setMenus(eventMenu, categoryMenu, templateMenu, timeUnitMenu);
        listener.disableMenuBar();
        eventMenu.setEnabled(true);

        menuBar.add(fileMenu);
        menuBar.add(eventMenu);
        menuBar.add(templateMenu);
        menuBar.add(categoryMenu);
        menuBar.add(timeUnitMenu);
        return menuBar;
    }

    public static JToolBar createToolbar(JButton addButton, JButton editButton, JButton deleteButton, Component... components) {
        var toolbar = new JToolBar();
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(deleteButton);
        toolbar.addSeparator();
        for (var component : components) {
            toolbar.add(component);
            toolbar.addSeparator(new Dimension(50, 10));
        }
        return toolbar;
    }

    public static JFrame createFrame() {
        var frame = new JFrame("TO-DO");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(MAXIMIZED_BOTH);
        return frame;
    }

    public static JPopupMenu createPopupMenu(ButtonTabStrategy buttonTabStrategy) {
        var menu = new JPopupMenu();
        menu.add(buttonTabStrategy.getAddAction());
        menu.add(buttonTabStrategy.getEditAction());
        menu.add(buttonTabStrategy.getDeleteAction());
        return menu;
    }
}
