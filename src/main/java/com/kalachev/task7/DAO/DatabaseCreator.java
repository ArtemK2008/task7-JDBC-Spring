package com.kalachev.task7.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {

	private static final String URL = "jdbc:postgresql://localhost/";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "2487";

	public void createDatabase() throws DAOException {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getDBConnection();
			statement = connection.createStatement();
			String dropIfExist = "DROP DATABASE IF EXISTS comkalachevtasksqljdbc;";
			statement.execute(dropIfExist);
			String createDb = "CREATE DATABASE comkalachevtasksqljdbc";
			statement.execute(createDb);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(statement, connection);
		}
	}

	public void createUser() throws DAOException {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getDBConnection();
			statement = connection.createStatement();
			String createUser = "DROP USER IF EXISTS kalachevartemsql;"
					+ "CREATE USER kalachevartemsql WITH  PASSWORD '1234';"
					+ " GRANT ALL PRIVILEGES ON DATABASE comkalachevtasksqljdbc TO kalachevartemsql;";
			statement.execute(createUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException();
		} finally {
			closeAll(statement, connection);
		}
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
