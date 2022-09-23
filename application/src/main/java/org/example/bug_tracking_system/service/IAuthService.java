package org.example.bug_tracking_system.service;

import org.example.bug_tracking_system.entity.User;

import javax.servlet.http.HttpSession;

public interface IAuthService {

	/**
	 * Get the currently signed in user from a session if it is valid
	 *
	 * @param session The session to look for user in
	 * @return The user if found and valid, else null
	 */
	User getUserFromSession(HttpSession session);

	/**
	 * Import a new user into the system
	 *
	 * @param user The new user to import
	 * @return Whether the import was successful
	 */
	boolean importUser(User user);

	/**
	 * Register an imported user into the system
	 *
	 * @param user The user to register and update
	 * @return Whether the registration was successful
	 */
	boolean registerUser(User user);

	/**
	 * Log in a registered user into a session
	 *
	 * @param email    The email of the user trying to login
	 * @param password The password of the user trying to login
	 * @param session  The session into which the user should be logged into
	 * @return The user if login succeeded, else null
	 */
	User loginUser(String email, String password, HttpSession session);

	/**
	 * Sign out the currently signed in user from a session
	 *
	 * @param session The session to sign out from
	 */
	void logoutSession(HttpSession session);

}
