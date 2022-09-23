package org.example.bug_tracking_system.storage;

import org.example.bug_tracking_system.entity.Bug;
import org.example.bug_tracking_system.entity.Project;
import org.example.bug_tracking_system.entity.User;

import java.util.List;

public interface IBugDao {

	/**
	 * Add a new bug into the database
	 *
	 * @param bug The bug to add
	 */
	void addOne(Bug bug);

	/**
	 * Retrieve bugs reported for a project
	 *
	 * @param project The project to use for querying
	 * @return The list of bugs (can be empty)
	 */
	List<Bug> getManyByProject(Project project);

	/**
	 * Retrieve the bug assigned to a developer
	 *
	 * @param developer The developer to which it is assigned
	 * @return The bug if assigned, else null
	 */
	Bug getOneByDeveloper(User developer);

	/**
	 * Get a single bug from the database
	 *
	 * @param id The ID of the requested bug
	 * @return The bug if found, else null
	 */
	Bug getOne(int id);

	/**
	 * Update the details of an existing bug in the database
	 *
	 * @param bug The bug with updated details (must have `id` set)
	 */
	void updateOne(Bug bug);

}
