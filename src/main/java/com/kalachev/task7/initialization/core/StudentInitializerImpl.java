package com.kalachev.task7.initialization.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.kalachev.task7.initialization.initialization_interfaces.StudentInitializer;

public class StudentInitializerImpl implements StudentInitializer {
  Random randomGenerator;

  public StudentInitializerImpl() {
    randomGenerator = new Random();
  }

  public StudentInitializerImpl(int seed) {
    super();
    this.randomGenerator = new Random(seed);
  }

  @Override
  public List<String> generateStudents() {
    List<String> students = new ArrayList<>();
    int roll;
    for (int i = 0; i < 200; i++) {
      roll = randomGenerator.nextInt(2) + 1;
      if (roll == 1) {
        students.add(handleGenderCase("mName", "mLastName"));
      }
      if (roll == 2) {
        students.add(handleGenderCase("fName", "fLastName"));
      }
    }

    return students;
  }

  private HashMap<String, List<String>> hardcodeData() {
    List<String> namesMale = new ArrayList<>();
    namesMale.add("Artem");
    namesMale.add("John");
    namesMale.add("Mike");
    namesMale.add("Ivan");
    namesMale.add("Nikolay");
    namesMale.add("Peter");
    namesMale.add("Gomer");
    namesMale.add("Eric");
    namesMale.add("Kyle");
    namesMale.add("Ilon");

    List<String> namesFemale = new ArrayList<>();
    namesFemale.add("Svetlana");
    namesFemale.add("Gale");
    namesFemale.add("Elena");
    namesFemale.add("Sonya");
    namesFemale.add("Kitana");
    namesFemale.add("Gwen");
    namesFemale.add("Viktoria");
    namesFemale.add("Jane");
    namesFemale.add("Oksana");
    namesFemale.add("Wendy");

    List<String> lastNamesMale = new ArrayList<>();
    lastNamesMale.add("Kalachev");
    lastNamesMale.add("Musk");
    lastNamesMale.add("Dashkivich");
    lastNamesMale.add("Ivanov");
    lastNamesMale.add("Smith");
    lastNamesMale.add("Petrov");
    lastNamesMale.add("Sidorov");
    lastNamesMale.add("Tesla");
    lastNamesMale.add("Makarevich");
    lastNamesMale.add("Trump");

    List<String> lastNamesFemale = new ArrayList<>();
    lastNamesFemale.add("Ahmatova");
    lastNamesFemale.add("Petrova");
    lastNamesFemale.add("Nosova");
    lastNamesFemale.add("Gorbacheva");
    lastNamesFemale.add("Holodnaya");
    lastNamesFemale.add("Morozova");
    lastNamesFemale.add("Lermontova");
    lastNamesFemale.add("Pavlova");
    lastNamesFemale.add("Ivanova");
    lastNamesFemale.add("Kuleshova");

    HashMap<String, List<String>> dataForRandomizing = new HashMap<>();
    dataForRandomizing.put("mName", namesMale);
    dataForRandomizing.put("fName", namesFemale);
    dataForRandomizing.put("mLastName", lastNamesMale);
    dataForRandomizing.put("fLastName", lastNamesFemale);
    return dataForRandomizing;
  }

  private String handleGenderCase(String nameGender, String lastNameGender) {
    HashMap<String, List<String>> data = hardcodeData();
    List<String> genderedName = data.get(nameGender);
    String currName = genderedName.get(randomGenerator.nextInt(10));
    List<String> genderedLastName = data.get(lastNameGender);
    String currLastName = genderedLastName.get(randomGenerator.nextInt(10));
    return currName + " " + currLastName;
  }

}
