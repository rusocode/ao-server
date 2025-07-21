

package com.ao.service.user;

import java.util.HashSet;
import java.util.Set;

import com.ao.model.user.ConnectedUser;
import com.ao.service.UserService;

public class UserServiceImpl implements UserService {

	private final Set<ConnectedUser> connectedUsers = new HashSet<ConnectedUser>();

	@Override
	public boolean isLoggedIn(final ConnectedUser user) {
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
