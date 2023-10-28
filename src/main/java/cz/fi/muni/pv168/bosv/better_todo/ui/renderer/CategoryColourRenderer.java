package cz.fi.muni.pv168.bosv.better_todo.ui.renderer;

import lombok.NonNull;

import javax.swing.*;
import java.awt.*;

public class CategoryColourRenderer extends AbstractRenderer<Color> {
    public CategoryColourRenderer() {
        super(Color.class);
    }

    @Override
    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
        cell.setBackground((Color) value);
        return cell;
    }


    @Override
    protected void updateLabel(@NonNull JLabel label, @NonNull Color value) {
        label.setText("");
    }
}
