package com.kalachev.task7.ui.commands.impl;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.task7.service.StudentOptions;
import com.kalachev.task7.ui.commands.Command;

@Component
public class DeleteByIdCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";
  @Autowired
  Scanner scanner;
  @Autowired
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
    if (!options.checkIfStudentIdExists(id)) {
      System.out.println("no such student");
      return;
    }
    if (options.deleteStudentById(id)) {
      System.out.println("student with id " + id + " deleted");
    }
  }
}
