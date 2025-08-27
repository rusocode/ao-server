package com.ao.model.user;

import com.ao.network.Connection;

public interface User {

    /**
     * Retrieves the associated connection for this user.
     *
     * @return the connection for this user
     */
    Connection getConnection();

}

