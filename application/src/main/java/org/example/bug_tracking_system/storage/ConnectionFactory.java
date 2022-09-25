package org.example.bug_tracking_system.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

	// Configuration constants
	private static final String PATH_TO_SQLITE_DB = "main.db";
	//TODO: Make constants out of table names

	// State variables
	private static boolean is_initialised = false;

	static Connection getConnection() {
		try {
			Connection con = DriverManager.getConnection("jdbc:sqlite:" + PATH_TO_SQLITE_DB);
			con.setAutoCommit(true);

			if (is_initialised) return con;
			initTables(con);
			is_initialised = true;
			return con;

		} catch (SQLException e) {
			throw new RuntimeException("Couldn't connect to database", e);
		}
	}

	private static void initTables(Connection con) {
		try {
			Statement s = con.createStatement();

			s.execute("CREATE TABLE IF NOT EXISTS \"users\" (" +
					"\"id\" INTEGER PRIMARY KEY AUTOINCREMENT," +
					"\"name\" TEXT NOT NULL," +
					"\"email\" TEXT NOT NULL," +
					"\"password_hash\" BLOB NULL," +
					"\"password_salt\" BLOB NULL," +
					"\"last_login\" DATETIME NOT NULL," +
					"\"role\" INTEGER NOT NULL DEFAULT 0," +
					"UNIQUE (\"email\")" +
					")");

			s.execute("CREATE TABLE IF NOT EXISTS \"projects\" (" +
					"\"id\" INTEGER PRIMARY KEY AUTOINCREMENT," +
					"\"name\" TEXT NOT NULL," +
					"\"description\" TEXT NOT NULL," +
					"\"start_date\" DATE NOT NULL," +
					"\"status\" INTEGER NOT NULL DEFAULT 0," +
					"\"creator\" INTEGER NOT NULL," +
					"\"manager\" INTEGER NULL," +
					"\"tester\" INTEGER NULL," +
					"FOREIGN KEY (\"creator\") REFERENCES \"users\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT," +
					"FOREIGN KEY (\"manager\") REFERENCES \"users\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT," +
					"FOREIGN KEY (\"tester\") REFERENCES \"users\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT" +
					")");

			s.execute("CREATE TABLE IF NOT EXISTS \"project_developers\" (" +
					"\"project\" INTEGER NOT NULL," +
					"\"developer\" INTEGER NOT NULL," +
					"UNIQUE(\"project\",\"developer\")," +
					"FOREIGN KEY (\"project\") REFERENCES \"projects\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT," +
					"FOREIGN KEY (\"developer\") REFERENCES \"users\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT" +
					")");

			s.execute("CREATE TABLE IF NOT EXISTS \"bugs\" (" +
					"\"id\" INTEGER PRIMARY KEY AUTOINCREMENT," +
					"\"title\" TEXT NOT NULL," +
					"\"description\" TEXT NOT NULL," +
					"\"project\" INTEGER NOT NULL," +
					"\"creator\" INTEGER NOT NULL," +
					"\"opened_on\" DATETIME NOT NULL," +
					"\"assignee\" INTEGER NULL," +
					"\"is_open\" INTEGER NOT NULL DEFAULT 1," +
					"\"ready_to_close\" INTEGER NOT NULL DEFAULT 0," +
					"\"closer\" INTEGER NULL," +
					"\"severity\" INTEGER NOT NULL DEFAULT 0," +
					"\"closed_on\" DATETIME NOT NULL," +
					"FOREIGN KEY (\"project\") REFERENCES \"projects\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT," +
					"FOREIGN KEY (\"creator\") REFERENCES \"users\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT," +
					"FOREIGN KEY (\"assignee\") REFERENCES \"users\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT," +
					"FOREIGN KEY (\"closer\") REFERENCES \"users\"(\"id\") ON UPDATE CASCADE ON DELETE RESTRICT" +
					")");

		} catch (SQLException e) {
			throw new RuntimeException("Couldn't initialise DB tables", e);
		}
	}

	private ConnectionFactory() {
	}

}
