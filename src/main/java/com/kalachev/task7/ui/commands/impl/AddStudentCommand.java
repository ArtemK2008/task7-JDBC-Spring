package com.kalachev.task7.ui.commands.impl;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.task7.service.StudentOptions;
import com.kalachev.task7.ui.commands.Command;

@Component
public class AddStudentCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";
  @Autowired
  StudentOptions options;
  @Autowired
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
    if (options.checkIfStudentAlreadyInGroup(groupId, name, lastname)) {
      System.out.println("User Already exists");
      return false;
    }

    if (options.addNewStudent(name, lastname, groupId)) {
      System.out.println(
          "Student " + name + " " + lastname + " added to group " + groupId);
      isAdded = true;
    } else {
      System.out.println(BAD_INPUT);
    }
    return isAdded;
  }

}
