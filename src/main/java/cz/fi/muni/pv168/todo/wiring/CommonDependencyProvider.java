package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.repository.Repository;
import cz.fi.muni.pv168.todo.business.service.crud.CategoryCrudService;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.crud.TimeUnitCrudService;
import cz.fi.muni.pv168.todo.business.service.validation.CategoryValidator;
import cz.fi.muni.pv168.todo.business.service.validation.TimeUnitValidator;
import cz.fi.muni.pv168.todo.storage.sql.CategorySqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.TimeUnitSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.CategoryDao;
import cz.fi.muni.pv168.todo.storage.sql.dao.TimeUnitDao;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionConnectionSupplier;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutorImpl;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionManagerImpl;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.CategoryMapper;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.TimeUnitMapper;

/**
 * Common dependency provider for both production and test environment.
 *
 * @author Vojtech Sassmann
 */
public class CommonDependencyProvider implements DependencyProvider {

    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final Repository<TimeUnit> timeUnitRepository;
    private final Repository<Category> categoryRepository;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<TimeUnit> timeUnitCrudService;


    public CommonDependencyProvider(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var timeUnitDao = new TimeUnitDao(transactionConnectionSupplier);
        var timeUnitMapper = new TimeUnitMapper();
        var timeUnitValidator = new TimeUnitValidator();

        this.timeUnitRepository = new TimeUnitSqlRepository(
                timeUnitDao,
                timeUnitMapper
        );
        timeUnitCrudService = new TimeUnitCrudService(getTimeUnitRepository(), timeUnitValidator);

        var categoryDao = new CategoryDao(transactionConnectionSupplier);
        var categoryMapper = new CategoryMapper();
        var categoryValidator = new CategoryValidator();

        this.categoryRepository = new CategorySqlRepository(
                categoryDao,
                categoryMapper
        );

        categoryCrudService = new CategoryCrudService(getCategoryRepository(), categoryValidator);


    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public Repository<Category> getCategoryRepository() {
        return this.categoryRepository;
    }

    @Override
    public Repository<TimeUnit> getTimeUnitRepository() {
        return timeUnitRepository;
    }

    @Override
    public CrudService<Event> getEventCrudService() {
        return null;
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
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }
}
