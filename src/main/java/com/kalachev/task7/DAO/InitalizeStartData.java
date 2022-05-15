package com.kalachev.task7.DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.jdbc.ScriptRunner;

public class InitalizeStartData {

	static final String INSERT_GROUPS = "INSERT INTO Groups (group_name) VALUES (?)";
	static final String INSERT_COURSES = "INSERT INTO Courses (course_name,course_description) VALUES (?,?)";
	static final String RETRIEVE_COURSE_iD = "SELECT(course_id) FROM Courses WHERE course_name = (?)";

	String url = "jdbc:postgresql://localhost/task7";
	String username = "postgres";
	String password = "2487";

	public void createTables() throws FileNotFoundException, DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);

			ScriptRunner runner = new ScriptRunner(connection);
			File resourcesDirectory = new File("src/main/resources/StartupSqlData.sql");
			Reader reader = new BufferedReader(new FileReader(resourcesDirectory));
			runner.runScript(reader);
		} catch (SQLException e) {
			throw new DAOException("create tables");
		} finally {
			closeAll(statement, connection);
		}

	}

	public void populateGroups(List<String> groups) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.prepareStatement(INSERT_GROUPS);
			connection.setAutoCommit(false);
			for (String group : groups) {
				statement.setString(1, group);
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DAOException("populate groups");
		} finally {
			closeAll(statement, connection);
		}
	}

	public void pupulateStudents(List<String> students, Map<String, List<String>> groupsWithItsStudents)
			throws DAOException {

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			for (String student : students) {
				String curGroup = findGroupByStudent(student, groupsWithItsStudents);
				String findIdSQL = "SELECT(group_id) FROM Groups WHERE group_name = '" + curGroup + "'";
				rs = statement.executeQuery(findIdSQL);
				String groupId = null;
				if (rs.next()) {
					groupId = rs.getString(1);
				}
				String insertStudentsSQL = "INSERT INTO Students (group_id,first_name, last_name) VALUES (" + "'"
						+ groupId + "','" + retrieveFirstName(student) + ("','") + retrieveLastName(student) + "')";
				statement.executeUpdate(insertStudentsSQL);
			}
		} catch (SQLException e) {
			throw new DAOException("populate students");
		} finally {
			closeAll(rs, statement, connection);
		}
	}

	public void populateCourses(Map<String, String> courses) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.prepareStatement(INSERT_COURSES);
			connection.setAutoCommit(false);
			for (String course : courses.keySet()) {
				statement.setString(1, course);
				statement.setString(2, courses.get(course));
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DAOException("populate courses");
		} finally {
			closeAll(statement, connection);
		}
	}

	public void createManyToMany(Map<String, List<String>> coursesOfStudent) throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			for (String id : coursesOfStudent.keySet()) {

				List<String> courses = coursesOfStudent.get(id);
				for (String course : courses) {
					String findIdSQL = "SELECT(course_id) FROM Courses WHERE course_name = '" + course + "'";
					rs = statement.executeQuery(findIdSQL);
					String courseId = null;
					if (rs.next()) {
						courseId = rs.getString(1);
					}
					String insertStudentsCourses = "INSERT INTO Students_Courses (student_id, course_id) VALUES ('" + id
							+ "','" + courseId + "')";
					statement.executeUpdate(insertStudentsCourses);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, statement, connection);
		}
	}

	public void createStudentsCoursesFullTable() throws DAOException {
		Connection connection = null;
		Statement statement = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			String SQL = "CREATE TABLE StudentsCoursesData" + " AS "
					+ "( SELECT s.student_id, s.first_name, s.last_name ,c.course_name,c.course_description "
					+ "FROM Students as s" + " INNER JOIN Students_courses as sc" + " ON s.student_id = sc.student_id "
					+ "INNER JOIN Courses as c " + "ON sc.course_id = c.course_id " + "ORDER BY s.student_id)";
			statement.executeUpdate(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(statement, connection);
		}
	}

	public Map<String, String> retrieveStudentsId() throws DAOException {
		Map<String, String> idsOfEachStuden = new LinkedHashMap<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT student_id,first_name,last_name  FROM Students");
			while (rs.next()) {
				idsOfEachStuden.put(rs.getString("student_id"),
						rs.getString("first_name") + " " + rs.getString("last_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("retrieve student_id");
		} finally {
			closeAll(rs, statement, connection);
		}
		return idsOfEachStuden;
	}

	private String findGroupByStudent(String student, Map<String, List<String>> groupsWithItsStudents)
			throws DAOException {
		String groupOfAStudent = "";
		for (Entry<String, List<String>> entry : groupsWithItsStudents.entrySet()) {
			if (entry.getValue().contains(student)) {
				groupOfAStudent = entry.getKey();
			}
		}
		if (groupOfAStudent.equals("")) {
			throw new DAOException("group not found");
		}
		return groupOfAStudent;
	}

	private String retrieveFirstName(String student) {
		int spaceIndex = student.indexOf(' ');
		return student.substring(0, spaceIndex);
	}

	private String retrieveLastName(String student) {
		int spaceIndex = student.indexOf(' ');
		return student.substring(spaceIndex + 1);
	}

	private void closeAll(ResultSet rs, Statement statement, Connection connection) throws DAOException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAOException("rs is null");
			}
		}
		{
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException("statement is null");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DAOException("connection is null");
				}
			}
		}
	}

	private void closeAll(Statement statement, Connection connection) throws DAOException {
		{
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException("statement is null");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DAOException("connection is null");
				}
			}
		}
	}
}
