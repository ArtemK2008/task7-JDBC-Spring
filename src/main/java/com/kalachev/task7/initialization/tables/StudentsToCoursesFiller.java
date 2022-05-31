package com.kalachev.task7.initialization.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionMaker;
import com.kalachev.task7.utilities.JdbcCloser;

public class StudentsToCoursesFiller {
  public void createManyToManyTable(Map<String, List<String>> coursesOfStudent)
      throws DaoException {

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionMaker.getDbConnectionForNewUser();
      statement = connection.createStatement();
      for (Entry<String, List<String>> entry : coursesOfStudent.entrySet()) {
        List<String> courses = entry.getValue();
        for (String course : courses) {
          String findIdSql = "SELECT(course_id) FROM Courses "
              + "WHERE course_name = '" + course + "'";
          rs = statement.executeQuery(findIdSql);
          String courseId = null;
          if (rs.next()) {
            courseId = rs.getString(1);
          }
          String insertStudentsCourses = "INSERT INTO Students_Courses (student_id, course_id) "
              + "VALUES ('" + entry.getKey() + "','" + courseId + "')";
          statement.executeUpdate(insertStudentsCourses);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      JdbcCloser.closeAll(rs, statement, connection);
    }
  }

  public void createStudentsCoursesFullTable() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = ConnectionMaker.getDbConnectionForNewUser();
      statement = connection.createStatement();
      String sql = "CREATE TABLE StudentsCoursesData" + " AS "
          + "( SELECT s.student_id,s.group_id, s.first_name, s.last_name ,c.course_name,c.course_description "
          + "FROM Students as s" + " INNER JOIN Students_courses as sc"
          + " ON s.student_id = sc.student_id " + "INNER JOIN Courses as c "
          + "ON sc.course_id = c.course_id " + "ORDER BY s.student_id)";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      JdbcCloser.closeAll(statement, connection);
    }
  }
}
