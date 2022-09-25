package org.example.bug_tracking_system.service;

import org.example.bug_tracking_system.entity.SecretCredential;
import org.example.bug_tracking_system.entity.User;
import org.example.bug_tracking_system.entity.UserRole;
import org.example.bug_tracking_system.storage.DaoFactory;
import org.example.bug_tracking_system.storage.IUserDao;

import javax.servlet.http.HttpSession;

public class AuthService implements IAuthService {

	private static final int INACTIVITY_TIMEOUT_SEC = 300;

	private final IUserDao userDao;

	public AuthService() {
		userDao = new DaoFactory().getUserDao();
	}

	public User getUserFromSession(HttpSession session) {
		// TODO: Add session validity checks
		return (User) session.getAttribute("user");
	}

	public boolean importUser(User user) {
		// TODO: Add email format validation
		if (user == null || ("" + user.getName()).isBlank() || ("" + user.getEmail()).isBlank())
			return false;

		try {
			userDao.addOne(user);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public boolean registerUser(String email, String password, String role) {
		if (email == null || password == null || role==null)
			return false;

		UserRole ur;
		try {
			ur = UserRole.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		}

		User target = userDao.getOneByEmail(email);
		if (target != null && target.getCredential() == null && target.getRole() == ur) {
			// TODO: Password strength checks
			target.setCredential(new SecretCredential(password));
			userDao.updateOne(target);
		}
		return false;
	}

	public boolean loginUser(String email, String password, HttpSession session) {
		User target = userDao.getOneByEmail(email);
		SecretCredential sc = target.getCredential();

		if (sc != null && sc.verify(password)) {
			target.updateLastLogin();
			session.setAttribute("user", target);
			userDao.updateOne(target);
			return true;
		}
		return false;
	}

	public void logoutSession(HttpSession session) {
		// TODO: Cleanup actions
		session.removeAttribute("user");
	}

}
