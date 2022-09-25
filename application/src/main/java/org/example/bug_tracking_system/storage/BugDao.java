package org.example.bug_tracking_system.storage;

import org.example.bug_tracking_system.entity.Bug;
import org.example.bug_tracking_system.entity.BugSeverity;
import org.example.bug_tracking_system.entity.Project;
import org.example.bug_tracking_system.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BugDao implements IBugDao {

	private final Connection db;
	private IProjectDao projectDao;
	private IUserDao userDao;

	BugDao(Connection db) {
		this.db = db;
	}

	public void init(IProjectDao projectDao, IUserDao userDao) {
		this.projectDao = projectDao;
		this.userDao = userDao;
	}

	public void addOne(Bug bug) {
		try {
			PreparedStatement s = db.prepareStatement("INSERT INTO" +
					" bugs(title,description,opened_on,severity,project,creator)" +
					" VALUES(?,?,?,?,?,?)");
			s.setString(1, bug.getTitle());
			s.setString(2, bug.getDescription());
			s.setTimestamp(3, Timestamp.valueOf(bug.getOpenedOn()));
			s.setInt(4, bug.getSeverity().ordinal());
			s.setInt(5, bug.getProject().getId());
			s.setInt(6, bug.getCreator().getId());
			s.executeUpdate();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't add new bug report", e);
		}
	}

	public List<Bug> getManyByProject(Project project) {
		if (project == null) return Collections.emptyList();
		List<Bug> result = new ArrayList<>();
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM bugs WHERE project=?");
			s.setInt(1, project.getId());
			ResultSet r = s.executeQuery();
			while (r.next())
				result.add(getBugResult(r));
			r.close();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get bug reports of a project", e);
		}
		return result;
	}

	public Bug getOneByDeveloper(User developer) {
		if (developer == null) return null;
		Bug result = null;
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM bugs WHERE assignee=?");
			s.setInt(1, developer.getId());
			ResultSet r = s.executeQuery();
			if (r.next())
				result = getBugResult(r);
			r.close();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get bug assigned to developer", e);
		}
		return result;
	}

	public Bug getOne(int id) {
		if (id == 0) return null;
		Bug result = null;
		try {
			PreparedStatement s = db.prepareStatement("SELECT * FROM bugs WHERE id=?");
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			if (r.next())
				result = getBugResult(r);
			r.close();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't get bug using unique ID", e);
		}
		return result;
	}

	private Bug getBugResult(ResultSet r) throws SQLException {
		return new Bug(
				r.getInt("id"),
				r.getString("title"),
				r.getString("description"),
				r.getTimestamp("opened_on").toLocalDateTime(),
				r.getTimestamp("closed_on").toLocalDateTime(),
				r.getInt("is_open") == 0,
				r.getInt("ready_to_close") != 0,
				BugSeverity.values()[r.getInt("severity")],
				projectDao.getOne(r.getInt("project")),
				userDao.getOne(r.getInt("creator")),
				userDao.getOne(r.getInt("assignee")),
				userDao.getOne(r.getInt("closer"))
		);
	}

	public void updateOne(Bug bug) {
		try {
			PreparedStatement s = db.prepareStatement("UPDATE bugs SET" +
					" description=?,assignee=?,is_open=?,ready_to_close=?,closer=?,closed_on=?" +
					" WHERE id=?"
			);
			s.setString(1, bug.getDescription());
			s.setInt(2, bug.getDeveloper() != null ? bug.getDeveloper().getId() : null);
			s.setInt(3, bug.isCompleted() ? 0 : 1);
			s.setInt(4, bug.isReadyToClose() ? 1 : 0);
			s.setInt(5, bug.getCloser() != null ? bug.getCloser().getId() : null);
			s.setTimestamp(6, bug.getClosedOn() != null ? Timestamp.valueOf(bug.getClosedOn()) : null);
			s.setInt(7, bug.getId());
			s.executeUpdate();
			s.close();

		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException("Couldn't modify bug", e);
		}
	}

}
