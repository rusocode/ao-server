package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.user.Account;

public interface AccountDAO {

    /**
     * Retrieves the account with the given name.
     *
     * @param username account's name
     * @return the account
     */
    Account retrieve(String username) throws DAOException;

    /**
     * Creates a new account.
     *
     * @param name     account's name
     * @param password account's password
     * @param mail     account's mail
     * @return the newly created account
     */
    Account create(String name, String password, String mail) throws DAOException, NameAlreadyTakenException;

    /**
     * Deletes the account with the given name.
     *
     * @param name the account to be deleted
     */
    void delete(String name);

    /**
     * Checks if the account with the given name exists.
     *
     * @param name account name
     * @return true if the account exists, false otherwise
     */
    boolean exists(String name);

}
