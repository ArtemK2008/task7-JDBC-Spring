package com.kalachev.task7.ui.commands;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import com.kalachev.task7.exceptions.StudentNotFoundException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.StudentOptions;

public class DeleteByIdCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;
  StudentOptions options;

  public DeleteByIdCommand(Scanner scanner, StudentOptions options) {
    super();
    this.scanner = scanner;
    this.options = options;
  }

  @Override
  public void execute() {
    System.out.println("Enter ID of a student you want to delete");
    String idInputed = scanner.next();
    if (!NumberUtils.isParsable(idInputed)) {
      System.out.println(BAD_INPUT);
      return;
    }
    int id = Integer.parseInt(idInputed);
    if (id < 1) {
      System.out.println("Wrong student id");
      return;
    }
    try {
      if (!options.checkIfStudentIdExists(id)) {
        System.out.println("no such student");
        return;
      }
      options.deleteStudentById(id);
      System.out.println("student with id " + id + " deleted");
    } catch (StudentNotFoundException | UiException e) {
      e.printStackTrace();
    }
  }

}
