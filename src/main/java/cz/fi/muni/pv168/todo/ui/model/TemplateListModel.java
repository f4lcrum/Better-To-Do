package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;

import java.util.ArrayList;
import java.util.List;

public class TemplateListModel extends RefreshableListModel<Template> {

    private List<Template> templates;
    private final CrudService<Template> templateCrudService;

    public TemplateListModel(CrudService<Template> templateCrudService) {
        this.templateCrudService = templateCrudService;
        this.templates = new ArrayList<>(templateCrudService.findAll());
    }

    @Override
    public int getSize() {
        return templates.size();
    }

    @Override
    public Template getElementAt(int index) {
        return templates.get(index);
    }

    @Override
    public void refresh() {
        this.templates = new ArrayList<>(templateCrudService.findAll());
        fireContentsChanged(this, 0, getSize() - 1);
    }
}
