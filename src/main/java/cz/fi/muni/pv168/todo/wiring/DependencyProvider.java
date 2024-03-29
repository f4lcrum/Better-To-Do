package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.export.ExportService;
import cz.fi.muni.pv168.todo.business.service.export.ImportService;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;

public interface DependencyProvider {

    CrudService<Event> getEventCrudService();

    CategoryCrudService getCategoryCrudService();

    CrudService<Template> getTemplateCrudService();

    CrudService<TimeUnit> getTimeUnitCrudService();

    Validator<Event> getEventValidator();

    Validator<Template> getTemplateValidator();

    Validator<TimeUnit> getTimeUnitValidator();

    Validator<Category> getCategoryValidator();

    ImportService getImportService();

    ExportService getExportService();

}
