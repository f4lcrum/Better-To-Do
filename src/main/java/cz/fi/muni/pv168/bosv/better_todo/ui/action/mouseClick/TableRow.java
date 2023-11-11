package cz.fi.muni.pv168.bosv.better_todo.ui.action.mouseClick;

import cz.fi.muni.pv168.bosv.better_todo.entity.Category;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

public class TableRow extends MouseAdapter {
    private final String title;

    public TableRow(String title) {
        this.title = title;
    }
    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getClickCount() == 2) {
            JTable target = (JTable) me.getSource();
            StringBuilder stringBuilder = new StringBuilder();

            int rowIndex = target.getSelectedRow();
            // colIndex begins from 1, ignoring color
            for (int colIndex = 1; colIndex < target.getColumnCount(); colIndex++) {
                var value = target.getValueAt(rowIndex, colIndex);
                if (value instanceof LocalDateTime) {
                    value = String.format("%d. %d. %d %02d:%02d", ((LocalDateTime) value).getDayOfMonth(), ((LocalDateTime) value).getMonthValue(), ((LocalDateTime) value).getYear(), ((LocalDateTime) value).getHour(), ((LocalDateTime) value).getMinute());
                } else if (value instanceof Category) {
                    value = ((Category) value).getName();
                }
                stringBuilder.append(target.getColumnName(colIndex)).append(": ").append(value).append("\n");
            }
            JOptionPane.showMessageDialog(null, stringBuilder.toString(), this.title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
