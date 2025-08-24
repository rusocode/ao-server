package com.ao.service;

import com.ao.model.user.ConnectedUser;
import com.ao.service.login.LoginErrorException;

public interface LoginService {

    /**
     * Attempts to connect the user using the given username and password.
     *
     * @param user       user trying to log in
     * @param username   character's username
     * @param password   character's password
     * @param version    client's version
     * @param clientHash client's integrity check hash
     */
    void connectExistingCharacter(ConnectedUser user, String username, String password, String version, String clientHash) throws LoginErrorException;

    /**
     * Attempts to connect a new character creating it with the given data.
     *
     * @param user       user trying to log in
     * @param username   character's name
     * @param password   character's password
     * @param race       character's race
     * @param gender     character's gender
     * @param archetype  character's archetype
     * @param head       character's head
     * @param mail       character's mail
     * @param homeland   character's homeland
     * @param clientHash client's integrity check hash
     * @param version    client's version
     */
    void connectNewCharacter(ConnectedUser user, String username, String password, byte race,
                             byte gender, byte archetype, int head, String mail,
                             byte homeland, String clientHash,
                             String version) throws LoginErrorException;

}
