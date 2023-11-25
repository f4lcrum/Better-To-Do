package cz.fi.muni.pv168.todo.entity;

import java.awt.Color;

public enum CategoryColor {
    RED(Color.RED),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    ORANGE(Color.ORANGE),
    PURPLE(new Color(128, 0, 128)), // Custom color
    CYAN(Color.CYAN),
    MAGENTA(Color.MAGENTA),
    BLACK(Color.BLACK),
    WHITE(Color.WHITE);

    private final Color color;

    CategoryColor(java.awt.Color color) {
        this.color = color;
    }

    public java.awt.Color getColor() {
        return color;
    }
}
