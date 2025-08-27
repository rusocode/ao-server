package com.ao.service.user;

import com.ao.model.user.ConnectedUser;
import com.ao.service.UserService;

import java.util.HashSet;
import java.util.Set;

public class UserServiceImpl implements UserService {

    private final Set<ConnectedUser> connectedUsers = new HashSet<ConnectedUser>();

    @Override
    public boolean isLoggedIn(ConnectedUser user) {
        return connectedUsers.contains(user);
    }

    @Override
    public void logIn(final ConnectedUser user) {
        connectedUsers.add(user);
    }

    @Override
    public void logOut(final ConnectedUser user) {
        connectedUsers.remove(user);
    }

}
