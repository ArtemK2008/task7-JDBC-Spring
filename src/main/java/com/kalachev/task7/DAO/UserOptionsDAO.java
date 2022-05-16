package com.kalachev.task7.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserOptionsDAO {

	private static final String FIND_GROUP_BY_SIZE = "SELECT g.group_name FROM Students as s INNER JOIN Groups as g ON s.group_id = g.group_id GROUP BY g.group_name HAVING COUNT (s.group_id) >=(?)";
	private static final String FIND_STUDENT = "SELECT student_id,first_name,last_name FROM studentscoursesdata WHERE course_name = (?)";
	private static final String INSERT_STUDENT = "INSERT INTO Students(group_id,first_name,last_name) VALUES (?,?,?)";
	private static final String DELETE_STUDENT = "DELETE FROM Students Where student_id = (?)";
	private static final String FIND_STUDENTS_FULLNAME = "SELECT student_id,first_name,last_name FROM studentscoursesdata WHERE student_id = (?)";
	private static final String FIND_COURSE_DESCRIPTION = "SELECT course_name,course_description FROM Courses WHERE course_name = (?)";
	private static final String ADD_STUDENT_TO_COURSE = "INSERT INTO studentscoursesdata(student_id,first_name,last_name,course_name,course_description) VALUES (?,?,?,?,?)";
	private static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM studentscoursesdata WHERE student_id = (?) AND course_name = (?)";
	private static final String CHECK_COURSE_IF_EXISTS = "SELECT course_name FROM studentscoursesdata WHERE course_name = (?)";
	private static final String CHECK_STUDENT_IF_EXISTS_IN_GROUP = "SELECT * FROM Students WHERE first_name = (?), last_name = (?), group_id = (?)";
	private static final String CHECK_STUDENT_ID_IF_EXISTS = "SELECT * FROM Students WHERE student_id = (?)";
	private static final String CHECK_IF_STUDENT_IN_COURSE = "SELECT * FROM studentscoursesdata WHERE student_id = (?), course_name = (?)";

	private static final String URL = "jdbc:postgresql://localhost/task7";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "2487";

	public List<String> findGroupsBySize(int maxSize) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> groups = new ArrayList<>();
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(FIND_GROUP_BY_SIZE);
			statement.setInt(1, maxSize);
			rs = statement.executeQuery();
			while (rs.next()) {
				groups.add(rs.getString("group_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(rs, statement, connection);
		}
		return groups;
	}

	public List<String> findStudentsByCourse(String courseName) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> students = new ArrayList<>();
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(FIND_STUDENT);
			statement.setString(1, courseName);
			rs = statement.executeQuery();
			while (rs.next()) {
				students.add(rs.getString("student_id") + " " + rs.getString("first_name") + " "
						+ rs.getString("last_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(rs, statement, connection);
		}
		return students;
	}

	public void addNewStudent(String firstName, String lastName, int groupId) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(INSERT_STUDENT);
			statement.setInt(1, groupId);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(statement, connection);
		}
	}

	public void deleteStudentById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(DELETE_STUDENT);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(statement, connection);
		}
	}

	public void addStudentToCourse(int studentId, String course) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(FIND_STUDENTS_FULLNAME);
			statement.setInt(1, studentId);
			rs = statement.executeQuery();
			String firstName = null;
			String lastName = null;
			if (rs.next()) {
				firstName = rs.getString("first_name");
				lastName = rs.getString("last_name");
			}
			rs.close();
			statement.close();
			statement = connection.prepareStatement(FIND_COURSE_DESCRIPTION);
			statement.setString(1, course);
			rs = statement.executeQuery();
			String courseDesciption = null;
			if (rs.next()) {
				courseDesciption = rs.getString("course_description");
			}
			rs.close();
			statement.close();
			statement = connection.prepareStatement(ADD_STUDENT_TO_COURSE);
			statement.setInt(1, studentId);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, course);
			statement.setString(5, courseDesciption);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(rs, statement, connection);
		}
	}

	public boolean removeStudentFromCourse(int studentId, String course) throws DAOException {
		boolean isRemoved = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(DELETE_STUDENT_FROM_COURSE);
			statement.setInt(1, studentId);
			statement.setString(2, course);
			int delCount = statement.executeUpdate();
			if (delCount != 0) {
				isRemoved = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(statement, connection);
		}
		return isRemoved;
	}

	public boolean checkCourseIfExists(String course) throws DAOException {
		boolean isExist = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(CHECK_COURSE_IF_EXISTS);
			statement.setString(1, course);
			rs = statement.executeQuery();
			if (rs.next()) {
				isExist = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(rs, statement, connection);
		}
		return isExist;
	}

	public boolean checkStudntIfExistsInGroup(String firstName, String lastName, int groupId) throws DAOException {
		boolean isExists = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(CHECK_STUDENT_IF_EXISTS_IN_GROUP);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setInt(3, groupId);
			rs = statement.executeQuery();
			if (rs.next()) {
				isExists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(rs, statement, connection);
		}
		return isExists;
	}

	public boolean checkStudentIdIfExists(int id) throws DAOException {
		boolean isExists = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(CHECK_STUDENT_ID_IF_EXISTS);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			if (rs.next()) {
				isExists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(rs, statement, connection);
		}
		return isExists;
	}

	public boolean checkIfStudentInCourse(int studentId, String course) throws DAOException {
		boolean isExists = false;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = getDBConnection();
			statement = connection.prepareStatement(CHECK_IF_STUDENT_IN_COURSE);
			statement.setInt(1, studentId);
			statement.setString(2, course);
			rs = statement.executeQuery();
			if (rs.next()) {
				isExists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(rs, statement, connection);
		}

		return isExists;
	}

	private void closeAll(Statement statement, Connection connection) throws DAOException {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException();
			}
		}
	}

	private void closeAll(ResultSet rs, Statement statement, Connection connection) throws DAOException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException();
			}
		}

	}

	private static Connection getDBConnection() throws DAOException {
		Connection connection = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException();
		}
	}

}
