package org.example.bug_tracking_system.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {

	private int id;
	private String name;
	private String email;
	private UserRole role;
	private SecretCredential credential;
	private LocalDateTime lastLogin;

	/** Create a new user */
	public User(String name, String email, UserRole role) {
		this.id = 0;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	/** Restore an existing user */
	public User(int id, String name, String email, UserRole role, SecretCredential credential, LocalDateTime lastLogin) {
		this(name, email, role);
		this.id = id;
		this.credential = credential;
		this.lastLogin = lastLogin;
	}

	@Override
	public String toString() {
		return "User{" + id + ",'" + name + "'}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User that = (User) o;
		if (this.id != 0 && that.id != 0)
			return id == ((User) o).id;
		return this.email.equals(that.email);
	}

	@Override
	public int hashCode() {
		return id != 0 ? id : Objects.hashCode(email);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public UserRole getRole() {
		return role;
	}

	public SecretCredential getCredential() {
		return credential;
	}

	public void setCredential(SecretCredential credential) {
		this.credential = credential;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void updateLastLogin() {
		this.lastLogin = LocalDateTime.now();
	}

}
