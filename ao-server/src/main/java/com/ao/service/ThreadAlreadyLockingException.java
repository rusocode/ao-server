
package com.ao.service;

/**
 * Exception to be thrown when a thread attempts to lock two sets of objects
 * @author mvanotti
 */
public class ThreadAlreadyLockingException extends Exception {

	private static final long serialVersionUID = 7289272631204994881L;

	public ThreadAlreadyLockingException() {
		super("Current thread is already locking some objects");
	}
}
