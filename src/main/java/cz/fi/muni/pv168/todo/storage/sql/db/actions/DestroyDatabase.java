package cz.fi.muni.pv168.todo.storage.sql.db.actions;

import cz.fi.muni.pv168.todo.storage.sql.db.DatabaseManager;

/**
 * @author Peter Stanko
 */
public final class DestroyDatabase {
    
    public static void main(String[] args) {
        var dbManager = DatabaseManager.createProductionInstance();
        dbManager.destroySchema();
    }
}
