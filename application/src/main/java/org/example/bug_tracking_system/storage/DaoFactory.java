package org.example.bug_tracking_system.storage;

import java.sql.Connection;

public class DaoFactory {

	private final IUserDao userDao;
	private final IProjectDao projectDao;
	private final IBugDao bugDao;

	public DaoFactory() {
		Connection db = ConnectionFactory.getConnection();
		userDao = new UserDao(db);
		bugDao = new BugDao(db);
		projectDao = new ProjectDao(db);
		((BugDao) bugDao).init(projectDao, userDao);
		((ProjectDao) projectDao).init(userDao, bugDao);
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public IProjectDao getProjectDao() {
		return projectDao;
	}

	public IBugDao getBugDao() {
		return bugDao;
	}
}
