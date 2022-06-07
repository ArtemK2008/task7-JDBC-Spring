package com.kalachev.task7.dao.initialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.kalachev.task7.dao.initialization_interfaces.CoursesInitializer;

public class CoursesInitializerImpl implements CoursesInitializer {
  Random random;

  public CoursesInitializerImpl() {
    super();
    random = new Random();
  }

  public CoursesInitializerImpl(int seed) {
    super();
    this.random = new Random(seed);
  }

  @Override
  public Map<String, String> generateCourses() {
    Map<String, String> courses = new LinkedHashMap<>();
    courses.put("English", "place to learn English");
    courses.put("Mandarin", "place to learn Mandarin");
    courses.put("Hindi", "place to learn Hindi");
    courses.put("Spanish", "place to learn Spanish");
    courses.put("French", "place to learn French");
    courses.put("Arabic", "place to learn Arabic");
    courses.put("Bengali", "place to learn Bengali");
    courses.put("Russian", "place to learn Russian");
    courses.put("Portuguese", "place to learn Portuguese");
    courses.put("Ukrainian", "place to learn Ukrainian");
    return courses;
  }

  @Override
  public List<String> retrieveCoursesNames(Map<String, String> courses) {
    if (courses == null || courses.isEmpty()) {
      throw new IllegalArgumentException();
    }
    return courses.entrySet().stream().map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }

  @Override
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
