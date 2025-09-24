package com.ao.service;

import com.ao.model.user.ConnectedUser;

public interface UserService {

    /**
     * Check if the ConnectedUser has logged in.
     *
     * @param user connected user
     * @return true if the ConnectedUser has logged in or false
     */
    boolean isLoggedIn(ConnectedUser user);

    /**
     * Log in a ConnectedUser.
     */
    void logIn(ConnectedUser user);

    /**
     * Log out a ConnectedUser.
     */
    void logOut(ConnectedUser user);

}
