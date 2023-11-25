package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.entity.CategoryColor;
import cz.fi.muni.pv168.todo.entity.Status;

import javax.swing.AbstractListModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CategoryColorListModel extends AbstractListModel<Color> {

    private final List<Color> colors;

    public CategoryColorListModel() {
        List<Color> colorList = new ArrayList<>();

        for (CategoryColor categoryColor : CategoryColor.values()) {
            colorList.add(categoryColor.getColor());
        }
        this.colors = colorList;
    }

    @Override
    public int getSize() {
        return colors.size();
    }

    @Override
    public Color getElementAt(int index) {
        return colors.get(index);
    }
}
