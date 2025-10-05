package com.ao.service;

import com.ao.model.user.ConnectedUser;
import com.ao.service.login.LoginErrorException;

/**
 * LoginService provides methods for user login and character creation within the game.
 * <p>
 * LoginService is <b>the server's "doorman"</b> - it decides who gets in, how they get in, and makes sure everything is in order
 * before allowing a player to enter the game world.
 */

public interface LoginService {

    /**
     * Attempts to connect the user using the given username and password.
     */
    void connectExistingCharacter(ConnectedUser user, String username, String password, String version, String clientHash) throws LoginErrorException;

    /**
     * Attempts to connect a new character creating it with the given data.
     */
    void connectNewCharacter(ConnectedUser user, String username, String password, int raceId, int genderId, byte archetype,
                             int head, String mail, byte cityId, String clientHash, String version) throws LoginErrorException;

}
