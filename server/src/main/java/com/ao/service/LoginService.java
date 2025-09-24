package com.ao.service;

import com.ao.model.user.ConnectedUser;
import com.ao.service.login.LoginErrorException;

public interface LoginService {

    /**
     * Attempts to connect the user using the given username and password.
     */
    void connectExistingCharacter(ConnectedUser user, String username, String password, String version, String clientHash) throws LoginErrorException;

    /**
     * Attempts to connect a new character creating it with the given data.
     */
    void connectNewCharacter(ConnectedUser user, String username, String password, byte race, byte gender, byte archetype, int head, String mail,
                             byte homeland, String clientHash, String version) throws LoginErrorException;

}
