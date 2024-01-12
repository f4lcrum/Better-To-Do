package cz.fi.muni.pv168.todo.business.service.export.batch;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;

import java.util.Collection;

public record Batch(
        Collection<Event> events,
        Collection<Category> categories,
        Collection<Template> templates,
        Collection<TimeUnit> timeUnits
) {
}
