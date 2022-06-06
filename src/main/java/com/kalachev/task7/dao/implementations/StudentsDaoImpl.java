package com.kalachev.task7.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class StudentsDaoImpl implements StudentsDao {

  private static final String FIND_STUDENTS = "SELECT student_id, group_id,first_name,last_name "
      + "FROM studentscoursesdata " + "WHERE course_name = (?)";

  private static final String INSERT_STUDENT = "INSERT INTO Students(group_id,first_name,last_name)"
      + " VALUES (?,?,?)";

  private static final String DELETE_STUDENT = "DELETE FROM Students Where student_id = (?)";

  @Override
  public List<Student> findByCourse(String courseName) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    List<Student> students = new ArrayList<>();
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(FIND_STUDENTS);
      statement.setString(1, courseName);
      rs = statement.executeQuery();
      while (rs.next()) {
        Student student = new Student();
        student.setId(rs.getInt("student_id"));
        student.setGroupdId(rs.getInt("group_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        students.add(student);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return students;
  }

  @Override
  public void insert(String firstName, String lastName, int groupId)
      throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(INSERT_STUDENT);
      statement.setInt(1, groupId);
      statement.setString(2, firstName);
      statement.setString(3, lastName);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }

  @Override
  public void delete(int id) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(DELETE_STUDENT);
      statement.setInt(1, id);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }

  @Override
  public Map<String, String> studentNamesById() throws DaoException {
    Map<String, String> idsOfEachStuden = new LinkedHashMap<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.createStatement();
      rs = statement.executeQuery(
          "SELECT student_id,first_name,last_name  FROM Students");
      while (rs.next()) {
        idsOfEachStuden.put(rs.getString("student_id"),
            rs.getString("first_name") + " " + rs.getString("last_name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return idsOfEachStuden;
  }
}
