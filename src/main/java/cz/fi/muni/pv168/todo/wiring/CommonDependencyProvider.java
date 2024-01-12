package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.crud.EventCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TemplateCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TimeUnitCrudService;
import cz.fi.muni.pv168.todo.business.service.export.ExportService;
import cz.fi.muni.pv168.todo.business.service.export.GenericExportService;
import cz.fi.muni.pv168.todo.business.service.export.GenericImportService;
import cz.fi.muni.pv168.todo.business.service.export.ImportService;
import cz.fi.muni.pv168.todo.business.service.validation.CategoryValidator;
import cz.fi.muni.pv168.todo.business.service.validation.EventValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TemplateValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TimeUnitValidator;
import cz.fi.muni.pv168.todo.business.service.validation.Validator;
import cz.fi.muni.pv168.todo.io.exports.JsonExporter;
import cz.fi.muni.pv168.todo.io.imports.JsonImporter;
import cz.fi.muni.pv168.todo.storage.sql.CategorySqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.EventSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.TemplateSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.TimeUnitSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.TransactionalImportService;
import cz.fi.muni.pv168.todo.storage.sql.dao.CategoryDao;
import cz.fi.muni.pv168.todo.storage.sql.dao.EventDao;
import cz.fi.muni.pv168.todo.storage.sql.dao.TemplateDao;
import cz.fi.muni.pv168.todo.storage.sql.dao.TimeUnitDao;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionConnectionSupplier;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutorImpl;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionManagerImpl;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.CategoryMapper;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.EventMapper;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.TemplateMapper;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.TimeUnitMapper;

import java.util.List;

/**
 * Common dependency provider for both production and test environment.
 *
 * @author Vojtech Sassmann
 */
public class CommonDependencyProvider implements DependencyProvider {

    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final Repository<Event> eventRepository;
    private final Repository<TimeUnit> timeUnitRepository;
    private final Repository<Category> categoryRepository;
    private final Repository<Template> templateRepository;
    private final CrudService<Event> eventCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;
    private final CrudService<Template> templateCrudService;
    private final ImportService importService;
    private final ExportService exportService;
    private final Validator<Event> eventValidator;
    private final Validator<Category> categoryValidator;
    private final Validator<TimeUnit> timeUnitValidator;
    private final Validator<Template> templateValidator;

    public CommonDependencyProvider(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var timeUnitDao = new TimeUnitDao(transactionConnectionSupplier);
        var timeUnitMapper = new TimeUnitMapper();
        this.timeUnitValidator = new TimeUnitValidator();
        this.timeUnitRepository = new TimeUnitSqlRepository(
                timeUnitDao,
                timeUnitMapper
        );
        this.timeUnitCrudService = new TimeUnitCrudService(this.timeUnitRepository, timeUnitValidator);

        var categoryDao = new CategoryDao(transactionConnectionSupplier);
        var categoryMapper = new CategoryMapper();
        this.categoryValidator = new CategoryValidator();
        this.categoryRepository = new CategorySqlRepository(
                categoryDao,
                categoryMapper
        );
        this.categoryCrudService = new CategoryCrudService(this.categoryRepository, categoryValidator);

        var templateDao = new TemplateDao(transactionConnectionSupplier);
        var templateMapper = new TemplateMapper(categoryDao, categoryMapper, timeUnitDao, timeUnitMapper);
        this.templateValidator = new TemplateValidator();
        this.templateRepository = new TemplateSqlRepository(
                templateDao,
                templateMapper
        );
        this.templateCrudService = new TemplateCrudService(this.templateRepository, templateValidator);

        var eventDao = new EventDao(transactionConnectionSupplier);
        var eventMapper = new EventMapper(categoryDao, categoryMapper, timeUnitDao, timeUnitMapper);
        this.eventValidator = new EventValidator();
        this.eventRepository = new EventSqlRepository(
                eventDao,
                eventMapper
        );
        this.eventCrudService = new EventCrudService(this.eventRepository, eventValidator);
        this.exportService = new GenericExportService(
                eventCrudService,
                categoryCrudService,
                templateCrudService,
                timeUnitCrudService,
                List.of(new JsonExporter()));
        var genericImportService = new GenericImportService(
                eventCrudService,
                categoryCrudService,
                templateCrudService,
                timeUnitCrudService,
                List.of(new JsonImporter())
        );
        this.importService = new TransactionalImportService(genericImportService, transactionExecutor);
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public Repository<Event> getEventRepository() {
        return eventRepository;
    }

    @Override
    public Repository<Category> getCategoryRepository() {
        return categoryRepository;
    }

    @Override
    public Repository<TimeUnit> getTimeUnitRepository() {
        return timeUnitRepository;
    }

    @Override
    public Repository<Template> getTemplateRepository() {
        return templateRepository;
    }

    @Override
    public CrudService<Event> getEventCrudService() {
        return eventCrudService;
    }

    @Override
    public CrudService<Category> getCategoryCrudService() {
        return categoryCrudService;
    }

    @Override
    public CrudService<TimeUnit> getTimeUnitCrudService() {
        return timeUnitCrudService;
    }

    @Override
    public CrudService<Template> getTemplateCrudService() {
        return templateCrudService;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }

    @Override
    public Validator<Event> getEventValidator() {
        return eventValidator;
    }

    @Override
    public Validator<Template> getTemplateValidator() {
        return templateValidator;
    }

    @Override
    public Validator<TimeUnit> getTimeUnitValidator() {
        return timeUnitValidator;
    }

    @Override
    public Validator<Category> getCategoryValidator() {
        return categoryValidator;
    }

    @Override
    public ImportService getImportService() {
        return importService;
    }

    @Override
    public ExportService getExportService() {
        return exportService;
    }
}
