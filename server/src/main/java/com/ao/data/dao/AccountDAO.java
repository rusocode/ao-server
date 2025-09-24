package com.ao.data.dao;

import com.ao.data.dao.exception.DAOException;
import com.ao.data.dao.exception.NameAlreadyTakenException;
import com.ao.model.user.Account;

public interface AccountDAO {

    Account get(String username) throws DAOException;

    Account create(String username, String password, String mail) throws DAOException, NameAlreadyTakenException;

    void delete(String username);

    boolean exists(String username);

}
