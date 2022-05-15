package com.kalachev.task7.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserOptions {

	static final String INSERT_STUDENT = "INSERT INTO Students(group_id,first_name,last_name) VALUES (?,?,?)";
	static final String DELETE_STUDENT = "DELETE FROM Students Where student_id = (?)";
	String url = "jdbc:postgresql://localhost/task7";
	String username = "postgres";
	String password = "2487";

	public void findGroupsBySize(int maxSize) throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			String SQL = "SELECT g.group_name " + "FROM Students as s " + "INNER JOIN Groups as g "
					+ "ON s.group_id = g.group_id " + "GROUP BY g.group_name " + "HAVING COUNT (s.group_id) >= "
					+ maxSize;
			rs = statement.executeQuery(SQL);
			while (rs.next()) {
				System.out.println(rs.getString("group_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("populate groups");
		} finally {
			closeAll(rs, statement, connection);
		}
	}

	public void findUsersByCourse(String courseName) throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			String SQL = "SELECT student_id,first_name,last_name " + "FROM studentscoursesdata "
					+ "WHERE course_name = '" + courseName + "'";
			rs = statement.executeQuery(SQL);
			while (rs.next()) {
				System.out.println(rs.getString("student_id") + " " + rs.getString("first_name") + " "
						+ rs.getString("last_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("findUsersByCourse");
		} finally {
			closeAll(rs, statement, connection);
		}
	}

	public void addNewStudent(String firstName, String LastName, int groupId) throws DAOException {
		if (groupId < 0 || groupId > 11) {
			throw new DAOException("Wrong group  id");
		}
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.prepareStatement(INSERT_STUDENT);
			statement.setInt(1, groupId);
			statement.setString(2, firstName);
			statement.setString(3, LastName);
			statement.executeUpdate();
			System.out.println("Student " + firstName + " " + LastName + " inserted!");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("findUsersByCourse");
		} finally {
			closeAll(rs, statement, connection);
		}
	}

	public void deleteStudentById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.prepareStatement(DELETE_STUDENT);
			statement.setInt(1, id);
			statement.executeUpdate();
			System.out.println("Student " + id + " deleted!");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("findUsersByCourse");
		} finally {
			closeAll(rs, statement, connection);
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

}
