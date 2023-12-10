package cz.fi.muni.pv168.todo.ui.action;

import cz.fi.muni.pv168.todo.ui.model.EntityTableModel;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Function;

public class DetailClick<T> extends MouseAdapter {

    private final EntityTableModel<T> tableModel;
    private final Function<T, String> formatter;
    private final String title;

    public DetailClick(EntityTableModel<T> tableModel, Function<T, String> formatter, String title) {
        this.tableModel = tableModel;
        this.formatter = formatter;
        this.title = title;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            JTable target = (JTable) mouseEvent.getSource();
            StringBuilder stringBuilder = new StringBuilder();

            int rowIndex = target.getSelectedRow();
            T entity = tableModel.getEntity(rowIndex);
            stringBuilder.append(formatter.apply(entity));
            JOptionPane.showMessageDialog(null, stringBuilder.toString(), this.title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
