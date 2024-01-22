package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.error.RuntimeApplicationException;

import java.io.Serial;

/**
 * Thrown, if entity does not contain UUID.
 *
 * @author Kristian Oravec
 * @since 1.0.0
 */
public class EntityNoUUIDException extends RuntimeApplicationException {

    @Serial
    private static final long serialVersionUID = 0L;

    public EntityNoUUIDException(String message) {
        super(message);
    }
}