package cz.fi.muni.pv168.todo.io.mapper;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.io.entity.IoCategory;
import cz.fi.muni.pv168.todo.io.entity.IoTemplate;
import cz.fi.muni.pv168.todo.io.entity.IoTimeUnit;

public class TemplateIoMapper implements IoMapper<Template, IoTemplate> {

    @Override
    public IoTemplate mapToIo(Template entity) {
        return new IoTemplate(
                entity.getGuid(),
                entity.getName(),
                entity.getEventName(),
                new IoCategory(
                        entity.getCategory().getGuid(),
                        entity.getCategory().getName(),
                        entity.getCategory().getColor()
                ),
                entity.getStartTime(),
                new IoTimeUnit(
                        entity.getTimeUnit().getGuid(),
                        entity.getTimeUnit().isDefault(),
                        entity.getTimeUnit().getName(),
                        entity.getTimeUnit().getHours(),
                        entity.getTimeUnit().getMinutes()
                ),
                entity.getDuration(),
                entity.getDescription()
        );
    }

    @Override
    public Template mapToBusiness(IoTemplate entity) {
        return new Template(
                entity.getId(),
                entity.getName(),
                entity.getEventName(),
                new Category(
                        entity.getCategory().getId(),
                        entity.getCategory().getName(),
                        entity.getCategory().getColor()
                ),
                entity.getStartTime(),
                new TimeUnit(
                        entity.getTimeUnit().getId(),
                        entity.getTimeUnit().getIsDefault(),
                        entity.getTimeUnit().getName(),
                        entity.getTimeUnit().getHours(),
                        entity.getTimeUnit().getMinutes()
                ),
                entity.getDuration(),
                entity.getDescription()
        );
    }
}
