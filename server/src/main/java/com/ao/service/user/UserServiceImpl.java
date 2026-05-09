package com.ao.service.user;

import com.ao.model.user.ConnectedUser;
import com.ao.service.UserService;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserServiceImpl implements UserService {

    private final Set<ConnectedUser> connectedUsers = new CopyOnWriteArraySet<>();

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

    @Override
    public Set<ConnectedUser> getConnectedUsers() {
        return Set.copyOf(connectedUsers);
    }

}
