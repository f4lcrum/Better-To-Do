package cz.fi.muni.pv168.todo.storage.sql.db.actions;

import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;

/**
 * @author Peter Stanko
 */
public final class CreateDatabase {
    public static void main(String[] args) {
        var dbManager = DatabaseManager.createProductionInstance();
        dbManager.initSchema();
        System.out.println("Database created...");
        System.out.println("Database connection string: " + dbManager.getDatabaseConnectionString());
    }
}
