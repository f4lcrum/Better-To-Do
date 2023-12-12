package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.business.repository.TimeUnitRepository;
import cz.fi.muni.pv168.todo.storage.sql.TimeUnitSqlRepository;
import cz.fi.muni.pv168.todo.storage.sql.dao.TimeUnitDao;
import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionConnectionSupplier;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionManagerImpl;
import cz.fi.muni.pv168.todo.storage.sql.entity.mapper.TimeUnitMapper;

public final class TestingDependencyProvider {

    private final TimeUnitRepository timeUnits;

    public TestingDependencyProvider(DatabaseManager databaseManager) {
        var transactionManager = new TransactionManagerImpl(databaseManager);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var timeUnitMapper = new TimeUnitMapper();

        this.timeUnits = new TimeUnitSqlRepository(
                new TimeUnitDao(transactionConnectionSupplier),
                timeUnitMapper
        );
    }

    public TimeUnitRepository getTimeUnitRepository() {
        return timeUnits;
    }
}

