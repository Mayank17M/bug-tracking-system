package org.example.bug_tracking_system.service;

import org.example.bug_tracking_system.entity.User;

import java.util.List;

public interface IFileService {

	/**
	 * Parse the given file and retrieve a list of users from it
	 *
	 * @param fileName The path to file containing user data in JSON form
	 * @return The list of users retrieved from the file
	 */
	List<User> getUsersFromJsonFile(String fileName);

	/**
	 * Parse the given file and retrieve a list of users from it
	 *
	 * @param fileName The path to file containing user data in XML form
	 * @return The list of users retrieved from the file
	 */
	List<User> getUsersFromXmlFile(String fileName);

}
