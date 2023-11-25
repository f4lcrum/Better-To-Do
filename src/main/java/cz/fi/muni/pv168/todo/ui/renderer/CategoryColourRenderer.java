package cz.fi.muni.pv168.todo.ui.renderer;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;

public class CategoryColourRenderer extends AbstractRenderer<Color> {

    public CategoryColourRenderer() {
        super(Color.class);
    }

    @Override
    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);
        cell.setBackground((Color) value);
        return cell;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    @Override
    public void setText(String t) {
        super.setText(t);
    }

    public Component getListCellRendererComponent(
            JList<? extends Color> list, Color value,
            int index, boolean isSelected, boolean cellHasFocus) {
        setText(" ");
        setBackground(value);
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

    @Override
    protected void updateLabel(JLabel label, Color value) {
        label.setText("");
    }
}
