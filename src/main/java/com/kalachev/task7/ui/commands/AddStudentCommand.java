package com.kalachev.task7.ui.commands;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.StudentOptions;

public class AddStudentCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  StudentOptions options;

  Scanner scanner;

  public AddStudentCommand(Scanner scanner, StudentOptions options) {
    super();
    this.scanner = scanner;
    this.options = options;
  }

  @Override
  public void execute() {
    System.out.println("Enter student name");
    String name = scanner.next();
    System.out.println("Enter student last name");
    String lastname = scanner.next();
    System.out.println("Enter group id to add this student");
    String groupInput = scanner.next();
    if (!NumberUtils.isParsable(groupInput)) {
      System.out.println(BAD_INPUT);
      return;
    }
    int groupId = Integer.parseInt(groupInput);
    if (groupId < 1 || groupId > 11) {
      System.out.println("Wrong groupd id");
      return;
    }
    addStudent(name, lastname, groupId);
  }

  private boolean addStudent(String name, String lastname, int groupId) {
    boolean isAdded = false;
    try {
      if (options.checkIfStudentAlreadyInGroup(groupId, name, lastname)) {
        System.out.println("User Already exists");
        return false;
      }
      options.addNewStudent(name, lastname, groupId);
      System.out.println(
          "Student " + name + " " + lastname + " added to group " + groupId);
      isAdded = true;
    } catch (UiException e) {
      System.out.println(BAD_INPUT);
    }
    return isAdded;
  }

}
