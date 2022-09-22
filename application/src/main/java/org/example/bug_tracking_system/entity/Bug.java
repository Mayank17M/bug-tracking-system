package org.example.bug_tracking_system.entity;

import java.time.LocalDateTime;

public class Bug {

	private int id;
	private String title;
	private String description;
	private LocalDateTime openedOn;
	private LocalDateTime closedOn;
	private boolean completed;
	private boolean readyToClose;
	private BugSeverity severity;

	private Project project;
	private User creator;
	private User developer;
	private User closer;

	/** Create a new bug report */
	public Bug(String title, String description, BugSeverity severity, Project project, User creator) {
		this.title = title;
		this.description = description;
		this.openedOn = LocalDateTime.now();
		this.severity = severity;
		this.project = project;
		this.creator = creator;
	}

	/** Restore an existing bug report */
	public Bug(int id, String title, String description, LocalDateTime openedOn, LocalDateTime closedOn, boolean completed, boolean readyToClose, BugSeverity severity, Project project, User creator, User developer, User closer) {
		this(title, description, severity, project, creator);
		this.id = id;
		this.openedOn = openedOn;
		this.closedOn = closedOn;
		this.completed = completed;
		this.readyToClose = readyToClose;
		this.developer = developer;
		this.closer = closer;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getOpenedOn() {
		return openedOn;
	}

	public LocalDateTime getClosedOn() {
		return closedOn;
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean isReadyToClose() {
		return readyToClose;
	}

	public void setReadyToClose(boolean readyToClose) {
		this.readyToClose = readyToClose;
	}

	public BugSeverity getSeverity() {
		return severity;
	}

	public Project getProject() {
		return project;
	}

	public User getCreator() {
		return creator;
	}

	public User getDeveloper() {
		return developer;
	}

	public void setDeveloper(User developer) {
		this.developer = developer;
	}

	public User getCloser() {
		return closer;
	}

	public void close(User closer) {
		// If the bug is already closed, ignore the request
		if(completed) return;

		completed = true;
		this.closer = closer;
		this.closedOn = LocalDateTime.now();
	}

}
