package cz.fi.muni.pv168.bosv.better_todo.ui.panels;

import cz.fi.muni.pv168.bosv.better_todo.ui.model.scaler.EntityTableColumnScaler;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public abstract class AbstractEntityResizeableJPanel extends JPanel {
    @Getter
    private final JTable table;
    private final EntityTableColumnScaler scaler;

    protected AbstractEntityResizeableJPanel(AbstractTableModel templateTableModel) {
        this.table = new JTable(templateTableModel);
        this.scaler = new EntityTableColumnScaler(table);
    }

    public void rescale(List<Integer> columnWidthPercent) {
        scaler.scale(columnWidthPercent, true);
    }
}
