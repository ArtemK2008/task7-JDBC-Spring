package com.kalachev.task7.ui.commands;

import java.util.Scanner;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.StudentOptions;
import com.kalachev.task7.service.validations.Validator;

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
    try {
      System.out.println("Enter student name");
      String name = scanner.next();
      System.out.println("Enter student last name");
      String lastname = scanner.next();
      System.out.println("Enter group id to add this student");
      int groupId = Integer.parseInt(scanner.next());
      if (groupId < 1 || groupId > 11) {
        System.out.println("Wrong groupd id");
        return;
      }
      addStudent(name, lastname, groupId);
    } catch (NumberFormatException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private boolean addStudent(String name, String lastname, int groupId) {
    boolean isAdded = false;
    try {
      if (Validator.checkIfStudentAlreadyInGroup(groupId, name, lastname)) {
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
