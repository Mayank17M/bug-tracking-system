package org.example.bug_tracking_system.storage;

import org.example.bug_tracking_system.entity.User;

public interface IUserDao {

	/**
	 * Import an user into the database
	 *
	 * @param user The user to import
	 */
	void addOne(User user);

	/**
	 * Get a single user from the database
	 *
	 * @param id The ID of the requested user
	 * @return The user if found, else null
	 */
	User getOne(int id);

	/**
	 * Get a single user from the database
	 *
	 * @param email The email address of the requested user
	 * @return The user if found, else null
	 */
	User getOneByEmail(String email);

	/**
	 * Update the details of an existing user in the database
	 *
	 * @param user The user with updated details (must have `id` set)
	 */
	void updateOne(User user);

}
