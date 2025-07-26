package com.ao.data.dao.exception;

import java.io.Serial;

/**
 * A general exception for DAOs.
 */

public class DAOException extends Exception {

    @Serial
    private static final long serialVersionUID = -438195738812585024L;

    /**
     * Create a new DAOException.
     */
    public DAOException() {
        super();
    }

    /**
     * Create a new DAOException with the given clause.
     *
     * @param cause cause of the exception
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new DAOException with the given message.
     *
     * @param message message of the exception
     */
    public DAOException(String message) {
        super(message);
    }

}
