package cz.fi.muni.pv168.todo.business.service.export.batch;

import cz.fi.muni.pv168.todo.business.error.RuntimeApplicationException;

/**
 * Exception thrown in case there is a problem with a bulk operation.
 */
public class BatchOperationException extends RuntimeApplicationException {
    public BatchOperationException(String message) {
        super(message);
    }

    public BatchOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
