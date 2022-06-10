package com.kalachev.task7.dao.implementations.spring.row_mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kalachev.task7.dao.entities.Course;

public class CourseRowMapper implements RowMapper<Course> {

  @Override
  public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
    Course course = new Course();
    course.setCourseName(rs.getString("course_name"));
    course.setCourseDescription(rs.getString("course_description"));
    return course;
  }
}
