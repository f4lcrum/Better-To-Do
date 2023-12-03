package cz.fi.muni.pv168.todo.business.service.crud;

import cz.fi.muni.pv168.todo.business.error.RuntimeApplicationException;

import java.io.Serial;

/**
 * Thrown, if there is already an existing entity.
 *
 * @author Vojtech Sassmann
 * @since 1.0.0
 */
public class EntityAlreadyExistsException extends RuntimeApplicationException {

    @Serial
    private static final long serialVersionUID = 0L;

    public EntityAlreadyExistsException(String message)
    {
        super(message);
    }
}
