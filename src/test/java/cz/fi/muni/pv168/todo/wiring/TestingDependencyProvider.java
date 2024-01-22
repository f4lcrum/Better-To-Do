package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.business.repository.CategoryRepository;
import cz.fi.muni.pv168.todo.business.repository.EventRepository;
import cz.fi.muni.pv168.todo.business.repository.TemplateRepository;
import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.CategorySqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.EventSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.TemplateSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.TimeUnitSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.CategoryDao;
import cz.fi.muni.pv168.todo.storage.sql.dao.EventDao;
import cz.fi.muni.pv168.todo.storage.sql.dao.TemplateDao;
import cz.fi.muni.pv168.todo.storage.sql.dao.TimeUnitDao;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionConnectionSupplier;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionManagerImpl;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.CategoryMapper;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.EventMapper;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.TemplateMapper;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.TimeUnitMapper;

public final class TestingDependencyProvider {

    private final TimeUnitRepository timeUnits;
    private final CategoryRepository categories;
    private final TemplateRepository templates;
    private final EventRepository events;

    public TestingDependencyProvider(DatabaseManager databaseManager) {
        var transactionManager = new TransactionManagerImpl(databaseManager);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var timeUnitMapper = new TimeUnitMapper();
        var categoryDao = new CategoryDao(transactionConnectionSupplier);
        var categoryMapper = new CategoryMapper();
        var templateMapper = new TemplateMapper(
                new CategoryDao(transactionConnectionSupplier),
                categoryMapper,
                new TimeUnitDao(transactionConnectionSupplier),
                timeUnitMapper
        );
        var eventMapper = new EventMapper(
                new CategoryDao(transactionConnectionSupplier),
                categoryMapper,
                new TimeUnitDao(transactionConnectionSupplier),
                timeUnitMapper
        );

        this.timeUnits = new TimeUnitSqlRepository(
                new TimeUnitDao(transactionConnectionSupplier),
                timeUnitMapper
        );

        this.categories = new CategorySqlRepository(
                categoryDao,
                categoryMapper
        );

        this.templates = new TemplateSqlRepository(
                new TemplateDao(transactionConnectionSupplier),
                templateMapper
        );

        this.events = new EventSqlRepository(
                new EventDao(transactionConnectionSupplier),
                eventMapper
        );

    }

    public TimeUnitRepository getTimeUnitRepository() {
        return timeUnits;
    }

    public CategoryRepository getCategoryRepository() {
        return categories;
    }

    public TemplateRepository getTemplateRepository() {
        return templates;
    }

    public EventRepository getEventRepository() {
        return events;
    }
}

