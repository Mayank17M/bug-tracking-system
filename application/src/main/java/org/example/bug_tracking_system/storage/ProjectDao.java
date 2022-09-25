package org.example.bug_tracking_system.storage;

import org.example.bug_tracking_system.entity.Bug;
import org.example.bug_tracking_system.entity.Project;
import org.example.bug_tracking_system.entity.User;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ProjectDao implements IProjectDao {

	private final Connection db;
	private IUserDao userDao;
	private IBugDao bugDao;

	ProjectDao(Connection db) {
		this.db = db;
	}

	public void init(IUserDao userDao, IBugDao bugDao) {
		this.userDao = userDao;
		this.bugDao = bugDao;
	}

	public void addOne(Project project) {
		try {
			PreparedStatement s = db.prepareStatement("INSERT INTO" +
					" projects(name,description,start_date,creator,manager)" +
					" VALUES(?,?,?,?,?)");
			s.setString(1, project.getName());
			s.setString(2, project.getDescription());
			s.setDate(3, Date.valueOf(project.getStartDate()));
			s.setInt(4, project.getCreator().getId());
			s.setInt(5, project.getManager().getId());
			s.executeUpdate();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't add new project", e);
		}
	}

	public List<Project> getManyByManager(User manager) {
		if (manager == null) return Collections.emptyList();
		List<Project> result = new ArrayList<>();
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM projects WHERE manager=?");
			s.setInt(1, manager.getId());
			ResultSet r = s.executeQuery();
			while (r.next())
				result.add(getProjectResult(r));
			r.close();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get projects of a manager", e);
		}
		return result;
	}

	public List<Project> getManyByTester(User tester) {
		if (tester == null) return Collections.emptyList();
		List<Project> result = new ArrayList<>();
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM projects WHERE tester=?");
			s.setInt(1, tester.getId());
			ResultSet r = s.executeQuery();
			while (r.next())
				result.add(getProjectResult(r));
			r.close();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get projects of a tester", e);
		}
		return result;
	}

	public Project getOne(int id) {
		if (id == 0) return null;
		Project result = null;
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM projects WHERE id=?");
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			if (r.next())
				result = getProjectResult(r);
			r.close();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get project using unique ID", e);
		}
		return result;
	}

	private Project getProjectResult(ResultSet r) throws SQLException {
		Set<User> developers = new HashSet<>();

		PreparedStatement s = db.prepareStatement("SELECT * FROM project_developers WHERE project=?");
		s.setInt(1, r.getInt("id"));
		ResultSet d = s.executeQuery();
		while (d.next())
			developers.add(userDao.getOne(d.getInt("developer")));

		return new Project(
				r.getInt("id"),
				r.getString("name"),
				r.getString("description"),
				r.getDate("start_date").toLocalDate(),
				r.getInt("status") != 0,
				userDao.getOne(r.getInt("creator")),
				userDao.getOne(r.getInt("manager")),
				userDao.getOne(r.getInt("tester")),
				developers
		);
	}

	public void updateOne(Project project) {
		try {
			db.setAutoCommit(false);
			PreparedStatement s = db.prepareStatement("UPDATE projects SET" +
					" name=?,description=?,status=?,creator=?,manager=?,tester=?" +
					" WHERE id=?"
			);
			s.setString(1, project.getName());
			s.setString(2, project.getDescription());
			s.setInt(3, project.isCompleted() ? 1 : 0);
			s.setInt(4, project.getCreator().getId());
			s.setInt(5, project.getManager() != null ? project.getManager().getId() : null);
			s.setInt(6, project.getTester() != null ? project.getTester().getId() : null);
			s.setInt(7, project.getId());
			s.executeUpdate();
			s.close();

			s = db.prepareStatement("DELETE FROM project_developers WHERE project=?");
			s.setInt(1, project.getId());
			s.executeUpdate();
			s.close();

			s = db.prepareStatement("INSERT INTO project_developers(project,developer) VALUES(?,?)");
			for (User d : project.getDevelopers()) {
				s.setInt(2, d.getId());
				s.setInt(1, project.getId());
				s.executeUpdate();
			}
			s.close();

		} catch (RuntimeException | SQLException e) {
			try {
				db.rollback();
				db.setAutoCommit(true);
			} catch (SQLException s) {
				throw new RuntimeException("Inconsistency in DB coming soon :(", s);
			}
			throw new RuntimeException("Couldn't modify project", e);
		} finally {
			try {
				db.setAutoCommit(true);
			} catch (SQLException e) {
				throw new RuntimeException("DB changes breaking soon :(", e);
			}
		}
	}

}
