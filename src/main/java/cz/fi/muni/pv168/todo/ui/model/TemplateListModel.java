package cz.fi.muni.pv168.todo.ui.model;

import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.ui.renderer.SpecialTemplateValues;
import cz.fi.muni.pv168.todo.util.Either;

import javax.swing.AbstractListModel;

import java.util.ArrayList;
import java.util.List;

public class TemplateListModel extends AbstractListModel<Either<Template, SpecialTemplateValues>>  {
    private List<Either<Template, SpecialTemplateValues>> templates;

    public TemplateListModel() {
        this.templates = new ArrayList<>();
    }

    public TemplateListModel(List<Either<Template, SpecialTemplateValues>> templates) {
        this.templates = templates;
    }

    @Override
    public int getSize() {
        return templates.size();
    }

    @Override
    public Either<Template, SpecialTemplateValues> getElementAt(int index) {
        return templates.get(index);
    }
}
