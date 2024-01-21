package cz.fi.muni.pv168.todo.business.service.export;

import cz.fi.muni.pv168.todo.business.entity.Category;
import cz.fi.muni.pv168.todo.business.entity.Event;
import cz.fi.muni.pv168.todo.business.entity.Template;
import cz.fi.muni.pv168.todo.business.entity.TimeUnit;
import cz.fi.muni.pv168.todo.business.service.crud.CrudService;
import cz.fi.muni.pv168.todo.business.service.export.batch.BatchImporter;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;

import cz.fi.muni.pv168.todo.ui.action.strategy.ImportStrategy;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class TransactionalImportService extends GenericImportService {

    private final TransactionExecutor transactionExecutor;
    private ImportStrategy strategy;

    public TransactionalImportService(
            CrudService<Event> eventCrudService,
            CrudService<Category> categoryCrudService,
            CrudService<Template> templateCrudService,
            CrudService<TimeUnit> timeUnitCrudService,
            Collection<BatchImporter> importers,
            TransactionExecutor transactionExecutor
    ) {
        super(eventCrudService, categoryCrudService, templateCrudService, timeUnitCrudService, importers);
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public boolean importData(String filePath) {
        AtomicBoolean result = new AtomicBoolean(false);
        transactionExecutor.executeInTransaction(() -> result.set(strategy.importData(filePath)));
        return result.get();
    }

    public void setStrategy(ImportStrategy strategy) {
        this.strategy = strategy;
    }
}
