package com.kalachev.task7.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.JdbcUtil;

public class CoursesDaoImpl implements CoursesDao {

  private static final String FIND_COURSE_DESCRIPTION = "SELECT course_name,course_description "
      + "FROM Courses WHERE course_name = (?)";

  private static final String FIND_STUDENTS_FULLNAME = "SELECT student_id,group_id,first_name,last_name "
      + "FROM students " + "WHERE student_id = (?)";

  private static final String ADD_STUDENT_TO_COURSE = "INSERT INTO studentscoursesdata"
      + "(student_id,group_id,first_name,last_name,course_name,course_description) "
      + "VALUES (?,?,?,?,?,?)";

  private static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM studentscoursesdata "
      + "WHERE student_id = (?) AND course_name = (?)";

  private static final String COURSE_DESCRIPTION = "course_description";

  private static final String CHECK_COURSE_IF_EXISTS = "SELECT course_name "
      + "FROM studentscoursesdata " + "WHERE course_name = (?)";

  @Override
  public void addStudent(int studentId, String course) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(FIND_STUDENTS_FULLNAME);
      statement.setInt(1, studentId);
      rs = statement.executeQuery();
      String firstName = null;
      String lastName = null;
      int groupId = 0;
      if (rs.next()) {
        firstName = rs.getString("first_name");
        lastName = rs.getString("last_name");
        groupId = rs.getInt("group_id");
      }
      rs.close();
      statement.close();
      statement = connection.prepareStatement(FIND_COURSE_DESCRIPTION);
      statement.setString(1, course);
      rs = statement.executeQuery();
      String courseDesciption = null;
      if (rs.next()) {
        courseDesciption = rs.getString(COURSE_DESCRIPTION);
      }
      rs.close();
      statement.close();
      statement = connection.prepareStatement(ADD_STUDENT_TO_COURSE);
      statement.setInt(1, studentId);
      statement.setInt(2, groupId);
      statement.setString(3, firstName);
      statement.setString(4, lastName);
      statement.setString(5, course);
      statement.setString(6, courseDesciption);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new DaoException("Error while adding Student with id " + studentId
          + " to course " + course);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
  }

  @Override
  public void removeStudent(int studentId, String course) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(DELETE_STUDENT_FROM_COURSE);
      statement.setInt(1, studentId);
      statement.setString(2, course);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new DaoException("Error while removing student with ID: "
          + studentId + " from course " + course);
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }

  @Override
  public List<Course> getAll() throws DaoException {
    List<Course> courses = new ArrayList<>();
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnection();
      String sql = "SELECT course_name, course_description FROM Courses";
      statement = connection.createStatement();
      rs = statement.executeQuery(sql);
      while (rs.next()) {
        Course course = new Course();
        course.setCourseName(rs.getString("course_name"));
        course.setCourseDescription(rs.getString(COURSE_DESCRIPTION));
        courses.add(course);
      }
    } catch (SQLException e) {
      throw new DaoException("Error while getting all existing courses");
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return courses;
  }

  @Override
  public List<Course> getById(int studentId) throws DaoException {
    List<Course> courses = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnection();
      String findCourse = "SELECT course_name,course_description FROM StudentsCoursesData"
          + " WHERE student_id = (?)";
      statement = connection.prepareStatement(findCourse);
      statement.setInt(1, studentId);
      rs = statement.executeQuery();
      while (rs.next()) {
        Course course = new Course();
        course.setCourseName(rs.getString("course_name"));
        course.setCourseDescription(rs.getString(COURSE_DESCRIPTION));
        courses.add(course);
      }
    } catch (SQLException e) {
      throw new DaoException(
          "Error while getting all assigned courses of a Student with ID: "
              + studentId);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return courses;
  }

  @Override
  public boolean isExists(String course) throws DaoException {
    boolean isExist = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(CHECK_COURSE_IF_EXISTS);
      statement.setString(1, course);
      rs = statement.executeQuery();
      if (rs.next()) {
        isExist = true;
      }
    } catch (SQLException e) {
      throw new DaoException(
          "Error while checking if course" + course + " exists");
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return isExist;
  }

}
