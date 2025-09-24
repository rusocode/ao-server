package com.ao.data.dao.exception;

import java.io.Serial;

/**
 * A general exception for DAOs.
 */

public class DAOException extends Exception {

    @Serial
    private static final long serialVersionUID = -438195738812585024L;

    public DAOException() {
        super();
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message) {
        super(message);
    }

}
