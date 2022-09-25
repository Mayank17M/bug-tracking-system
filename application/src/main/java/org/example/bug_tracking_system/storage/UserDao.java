package org.example.bug_tracking_system.storage;

import org.example.bug_tracking_system.entity.SecretCredential;
import org.example.bug_tracking_system.entity.User;
import org.example.bug_tracking_system.entity.UserRole;

import java.sql.*;

public class UserDao implements IUserDao {

	private final Connection db;

	UserDao(Connection db) {
		this.db = db;
	}

	public void addOne(User user) {
		try {
			PreparedStatement s = db.prepareStatement("INSERT INTO users(name,email,role) VALUES(?,?,?)");
			s.setString(1, user.getName());
			s.setString(2, user.getEmail());
			s.setInt(3, user.getRole().ordinal());
			s.executeUpdate();
			s.close();
		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't add new user", e);
		}
	}

	public User getOne(int id) {
		if (id == 0) return null;
		User result = null;
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM users WHERE id=?");
			s.setInt(1, id);
			result = getUserResult(s.executeQuery());
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get user using unique ID", e);
		}
		return result;
	}

	public User getOneByEmail(String email) {
		if (email == null) return null;
		User result = null;
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM users WHERE email=?");
			s.setString(1, email);
			result = getUserResult(s.executeQuery());
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get user using email", e);
		}
		return result;
	}

	private User getUserResult(ResultSet r) throws SQLException {
		User result = null;

		if (r.next()) {
			byte[] hash = r.getBytes("password_hash");
			byte[] salt = r.getBytes("password_salt");
			SecretCredential sc = null;
			if (hash != null && salt != null)
				sc = new SecretCredential(hash, salt);

			result = new User(
					r.getInt("id"),
					r.getString("name"),
					r.getString("email"),
					UserRole.values()[r.getInt("role")],
					sc,
					r.getTimestamp("last_login").toLocalDateTime()
			);
		}

		r.close();
		return result;
	}

	public void updateOne(User user) {
		try {
			PreparedStatement s = db.prepareStatement("UPDATE users SET" +
					" name=?,email=?,password_hash=?,password_salt=?,last_login=?,role=?" +
					" WHERE id=?");
			s.setString(1, user.getName());
			s.setString(2, user.getEmail());
			if (user.getCredential() != null) {
				s.setBytes(3, user.getCredential().getHash());
				s.setBytes(4, user.getCredential().getSalt());
			} else {
				s.setNull(3, Types.BLOB);
				s.setNull(4, Types.BLOB);
			}
			s.setTimestamp(5, Timestamp.valueOf(user.getLastLogin()));
			s.setInt(6, user.getRole().ordinal());
			s.setInt(7, user.getId());
			s.executeUpdate();
			s.close();
		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't modify user", e);
		}
	}

}
