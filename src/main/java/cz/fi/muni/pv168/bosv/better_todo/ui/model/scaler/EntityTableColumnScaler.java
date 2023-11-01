package cz.fi.muni.pv168.bosv.better_todo.ui.model.scaler;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.List;

@AllArgsConstructor
@NonNull
public class EntityTableColumnScaler {
    private final JTable table;

    public void scale(TableColumn column, int scale, int tableWidth) {
        if (scale < 0 || scale > 100) {
            throw new IllegalArgumentException(
                    String.format("Width should be between 0 - 100%, not %d!", scale)
            );
        }
        double newWidth = tableWidth * (scale / 100.0d);
        column.setPreferredWidth((int) newWidth);
    }


    public void scale(List<Integer> scales) {
        if (table.getColumnCount() < scales.size()) {
            throw new IllegalArgumentException("Scales count in the list must be at most as many as the available columns!");
        }
        for (int i = 0; i < scales.size(); i++) {
            this.scale(table.getColumnModel().getColumn(i), scales.get(i), table.getWidth());
        }
    }


    public void scale(List<Integer> scales, boolean setToScaleFromRight) {
        // Auto resize off so the columns won't jump
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.scale(scales);
        // Restore autoscale setting
        int resizeModeToUse = setToScaleFromRight ? JTable.AUTO_RESIZE_LAST_COLUMN : JTable.AUTO_RESIZE_ALL_COLUMNS;
        table.setAutoResizeMode(resizeModeToUse);
    }
}
