package com.ao.service;

import java.io.Serial;

/**
 * Exception to be thrown when a thread attempts to lock two sets of objects.
 */

public class ThreadAlreadyLockingException extends Exception {

    @Serial
    private static final long serialVersionUID = 7289272631204994881L;

    public ThreadAlreadyLockingException() {
        super("Current thread is already locking some objects");
    }

}
