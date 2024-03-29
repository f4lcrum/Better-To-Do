package cz.fi.muni.pv168.todo.io;

import cz.fi.muni.pv168.todo.business.error.RuntimeApplicationException;

/**
 * Exception thrown if there is some problem with data import/export.
 */
public final class DataManipulationException extends RuntimeApplicationException {

    public DataManipulationException(String message, Throwable cause) {
        super(message, cause);
    }

}
