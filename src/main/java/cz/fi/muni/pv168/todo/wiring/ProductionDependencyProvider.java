package cz.fi.muni.pv168.todo.wiring;

import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;
import cz.fi.muni.pv168.todo.wiring.CommonDependencyProvider;

/**
 * Dependency provider for production environment.
 * @author Vojtech Sassmann
 */
public class ProductionDependencyProvider extends CommonDependencyProvider {

    public ProductionDependencyProvider() { super(createDatabaseManager()); }

    private static DatabaseManager createDatabaseManager() {
        DatabaseManager databaseManager = DatabaseManager.createProductionInstance();
        databaseManager.initSchema();

        return databaseManager;
    }
}
