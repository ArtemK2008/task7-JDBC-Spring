package com.kalachev.task7.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CoursesOfEachStudent {
  Random random;

  public CoursesOfEachStudent() {
    super();
    random = new Random();
  }

  public CoursesOfEachStudent(int seed) {
    super();
    this.random = new Random(seed);
  }

  public Map<String, List<String>> assignStudentsIdToCourse(
      Map<String, String> studentsWithId, List<String> courses) {
    checkInput(studentsWithId, courses);
    int totalCourses = courses.size();

    Map<String, List<String>> coursesOfEachStudent = new HashMap<>();
    for (String id : studentsWithId.keySet()) {
      int amoutOfCourses = random.nextInt(3) + 1;
      List<String> coursesTaken = new ArrayList<>();
      List<String> coursesAlreadyEnrolled = new ArrayList<>();
      for (int i = 0; i < amoutOfCourses; i++) {
        String tempCourse = courses.get(random.nextInt(totalCourses));
        while (coursesAlreadyEnrolled.contains(tempCourse)) {
          tempCourse = courses.get(random.nextInt(totalCourses));
        }
        coursesTaken.add(tempCourse);
        coursesAlreadyEnrolled.add(tempCourse);
      }
      coursesOfEachStudent.put(id, coursesTaken);
    }
    return coursesOfEachStudent;
  }

  private void checkInput(Map<String, String> studentsWithId,
      List<String> courses) {
    if (studentsWithId == null || courses == null) {
      throw new IllegalArgumentException();
    }
    if (studentsWithId.isEmpty() || courses.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

}
