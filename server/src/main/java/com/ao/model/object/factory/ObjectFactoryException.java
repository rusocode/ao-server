package com.ao.model.object.factory;

import java.io.Serial;

/**
 * Exception for errors while building Objects.
 */

public class ObjectFactoryException extends Exception {

    @Serial
    private static final long serialVersionUID = -8073725671591764207L;

    /**
     * Creates a new exception with the given cause.
     *
     * @param e the cause of the exception
     */
    public ObjectFactoryException(Throwable e) {
        super(e);
    }

}
