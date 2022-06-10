package com.kalachev.task7.initialization.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.JdbcUtil;

public class StudentsToCoursesDataDbPopulator {
  public void createManyToManyTable(
      Map<String, List<String>> coursesOfStudent) {

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    try {
      connection = ConnectionManager.openDbConnection();
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
      System.out.println("Error while populating many to many table");
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
  }

  public void createStudentsCoursesFullTable() {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.createStatement();
      String sql = "CREATE TABLE StudentsCoursesData" + " AS "
          + "( SELECT s.student_id,s.group_id, s.first_name, s.last_name ,c.course_name,c.course_description "
          + "FROM Students as s" + " INNER JOIN Students_courses as sc"
          + " ON s.student_id = sc.student_id " + "INNER JOIN Courses as c "
          + "ON sc.course_id = c.course_id " + "ORDER BY s.student_id)";
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.out.println("Error while populating table Students_Courses");
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }
}
