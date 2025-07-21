

package com.ao.service;

import com.ao.model.user.ConnectedUser;

public interface UserService {

	/**
	 * Look if a ConnectedUser is logged in.
	 * @param user The ConnectedUser
	 * @return True if it is connected, False in another case.
	 */
	boolean isLoggedIn(ConnectedUser user);

	/**
	 * Log in a ConnectedUser
	 * @param user The ConnectedUser
	 */
	void logIn(ConnectedUser user);

	/**
	 * Log out a ConnectedUser
	 * @param user The ConnectedUser
	 */
	void logOut(ConnectedUser user);
}
