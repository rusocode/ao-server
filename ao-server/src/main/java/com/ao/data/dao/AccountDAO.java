package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.user.Account;

public interface AccountDAO {

	/**
	 * Retrieves the account with the given name.
	 * @param username 	The account's name.
	 * @return 			The account.
	 * @throws DAOException
	 */
	Account retrieve(String username) throws DAOException;
	
	/**
	 * Creates a new account.
	 * 
	 * @param name 		The account's name.
	 * @param password 	The account's password.
	 * @param mail 		The account's mail.
	 * @return			The new created account.
	 * @throws DAOException
	 */
	Account create(String name, String password, String mail) throws DAOException, NameAlreadyTakenException;
	
	/**
	 * Deletes the account with the given name.
	 * 
	 * @param name The account to be deleted.
	 */
	void delete(String name);
	
	/**
	 * Checks if the account with the given name exists.
	 * @param name The account name.
	 * @return True if the account exists, false otherwise.
	 */
	boolean exists(String name);
}
