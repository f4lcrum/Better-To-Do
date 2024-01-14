package cz.fi.muni.pv168.todo.util;

import cz.fi.muni.pv168.todo.business.service.export.format.Format;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;

public final class Filter extends FileFilter {

    private final String label;
    private final Collection<String> extensions;

    public Filter(Format format) {
        this.label = format.name();
        this.extensions = format.extensions();
    }

    public String decorate(String name) {
        if (getExtension(name).isEmpty())
            return String.format("%s.%s", name, extensions.stream().findFirst().orElseThrow());
        return name;
    }

    @Override
    public boolean accept(File pathname) {
        var extension = getExtension(pathname.getName());
        return extensions.stream()
                .anyMatch(extension::equals);
    }

    @Override
    public String getDescription() {
        return String.format("%s (%s)", label, extensions.stream()
                .map(e -> e.concat(".*"))
                .collect(Collectors.joining(", ")));
    }

    private static String getExtension(String name) {
        int dot = name.lastIndexOf('.');
        if (0 < dot && dot < name.length() - 1) {
            return name.substring(dot + 1).toLowerCase();
        }
        return "";
    }
}
