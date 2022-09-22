package org.example.bug_tracking_system.entity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class Project {

	private int id;
	private String name;
	private String description;
	private LocalDate startDate;
	private boolean completed;

	private User creator;
	private User manager;
	private User tester;
	private Set<User> developers;

	/** Create a new project */
	public Project(String name, String description, LocalDate startDate, User creator) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.creator = creator;
		this.manager = creator;
		this.developers = Collections.emptySet();
	}

	/** Restore an existing project */
	public Project(int id, String name, String description, LocalDate startDate, boolean completed, User creator, User manager, User tester, Set<User> developers) {
		this(name, description, startDate, creator);
		this.id = id;
		this.completed = completed;
		this.manager = manager;
		this.tester = tester;
		setDevelopers(developers);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public User getCreator() {
		return creator;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public User getTester() {
		return tester;
	}

	public void setTester(User tester) {
		this.tester = tester;
	}

	public Set<User> getDevelopers() {
		return developers;
	}

	public void setDevelopers(Set<User> developers) {
		if (developers == null)
			this.developers = Collections.emptySet();
		else
			this.developers = developers
					//.stream()
					//.filter(d -> !(d.equals(tester) || d.equals(manager)))
					//.collect(Collectors.toSet())
			;
	}

}
