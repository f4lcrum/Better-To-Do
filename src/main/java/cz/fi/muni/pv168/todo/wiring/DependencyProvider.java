package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;

public interface DependencyProvider {
    DatabaseManager getDatabaseManager();

    // Repository<Event> getEventRepository();
    Repository<Category> getCategoryRepository();

    Repository<Template> getTemplateRepository();

    Repository<TimeUnit> getTimeUnitRepository();

    // CrudService<Event> getEventCrudService();
    CrudService<Category> getCategoryCrudService();

    CrudService<Template> getTemplateCrudService();

    CrudService<TimeUnit> getTimeUnitCrudService();

    TransactionExecutor getTransactionExecutor();

}
