package com.ao.model.worldobject.factory;

import java.io.Serial;

/**
 * Exception for errors while building WorldObjects.
 */

public class WorldObjectFactoryException extends Exception {

    @Serial
    private static final long serialVersionUID = -8073725671591764207L;

    /**
     * Creates a new exception with the given cause.
     *
     * @param e the cause of the exception
     */
    public WorldObjectFactoryException(Throwable e) {
        super(e);
    }

}
