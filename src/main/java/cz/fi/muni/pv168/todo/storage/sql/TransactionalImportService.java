package cz.fi.muni.pv168.todo.storage.sql;

import cz.fi.muni.pv168.todo.business.service.export.ImportService;
import cz.fi.muni.pv168.todo.business.service.export.format.Format;
import cz.fi.muni.pv168.todo.storage.sql.db.TransactionExecutor;

import java.util.Collection;

public class TransactionalImportService implements ImportService {

    private final ImportService importService;
    private final TransactionExecutor transactionExecutor;

    public TransactionalImportService(ImportService importService, TransactionExecutor transactionExecutor) {
        this.importService = importService;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void importData(String filePath) {
        transactionExecutor.executeInTransaction(() -> importService.importData(filePath));
    }

    @Override
    public Collection<Format> getFormats() {
        return importService.getFormats();
    }
}
