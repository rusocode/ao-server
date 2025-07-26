package com.ao.service;

import com.ao.model.user.ConnectedUser;

public interface UserService {

	/**
	 * Look if a ConnectedUser is logged in.
	 *
	 * @param user connected user
	 * @return true if it is connected, false in another case
	 */
	boolean isLoggedIn(ConnectedUser user);

	/**
	 * Log in a ConnectedUser
	 *
	 * @param user connected user
	 */
	void logIn(ConnectedUser user);

	/**
	 * Log out a ConnectedUser
	 *
	 * @param user connected user
	 */
	void logOut(ConnectedUser user);

}
