package com.ao.data.dao.exception;

/**
 * A general exception for DAOs.
 * @author jsotuyod
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = -438195738812585024L;
	
	/**
	 * Create a new DAOException.
	 */
	public DAOException() {
		super();
	}
	
	/**
	 * Create a new DAOException with the given clause.
	 * @param cause The cause of the exception.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create a new DAOException with the given message.
	 * @param message The message of the exception.
	 */
	public DAOException(String message) {
		super(message);
	}
}
