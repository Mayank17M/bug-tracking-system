package org.example.bug_tracking_system.storage;

import org.example.bug_tracking_system.entity.Project;
import org.example.bug_tracking_system.entity.User;

import java.util.List;

public interface IProjectDao {

	/**
	 * Add a new project into the database
	 *
	 * @param project The project to add
	 */
	void addOne(Project project);

	/**
	 * Retrieve projects managed by a manager
	 *
	 * @param manager The manager user (must have `id` set)
	 * @return The list of projects (can be empty)
	 */
	List<Project> getManyByManager(User manager);

	/**
	 * Retrieve projects assigned to a tester
	 *
	 * @param tester The tester user (must have `id` set)
	 * @return The list of projects (can be empty)
	 */
	List<Project> getManyByTester(User tester);

	/**
	 * Retrieve a project from the database
	 *
	 * @param id The ID of the requested project
	 * @return The project if found, else null
	 */
	Project getOne(int id);

	/**
	 * Update the details of an existing project in the database
	 *
	 * @param project The project with updated details (must have `id` set)
	 */
	void updateOne(Project project);

}
