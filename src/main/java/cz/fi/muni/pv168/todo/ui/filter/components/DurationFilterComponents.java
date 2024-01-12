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
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public final class DurationFilterComponents {
    public final JPanel panel;
    public final JTextField minDurationField;
    public final JTextField maxDurationField;

    public DurationFilterComponents(JPanel panel, JTextField minDurationField, JTextField maxDurationField) {
        this.panel = panel;
        this.minDurationField = minDurationField;
        this.maxDurationField = maxDurationField;
    }
}
