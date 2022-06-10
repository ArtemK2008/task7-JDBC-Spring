package com.kalachev.task7.ui.dispatcher;

import com.kalachev.task7.ui.commands.Command;

public class CommandDispatcherImpl implements CommandDispatcher {
  Command groupSizeCommand;
  Command findStudentsByCourseCommand;
  Command addStudentCommand;
  Command deleteByIdCommand;
  Command addToCourseCommand;
  Command removeFromCourseCommand;
  Command exitCommand;

  public CommandDispatcherImpl(Command groupSizeCommand,
      Command findStudentsByCourseCommand, Command addStudentCommand,
      Command deleteByIdCommand, Command addToCourseCommand,
      Command removeFromCourseCommand, Command exitCommand) {
    super();
    this.groupSizeCommand = groupSizeCommand;
    this.findStudentsByCourseCommand = findStudentsByCourseCommand;
    this.addStudentCommand = addStudentCommand;
    this.deleteByIdCommand = deleteByIdCommand;
    this.addToCourseCommand = addToCourseCommand;
    this.removeFromCourseCommand = removeFromCourseCommand;
    this.exitCommand = exitCommand;
  }

  @Override
  public Command getGroupSizeCommand() {
    return groupSizeCommand;
  }

  @Override
  public Command getFindStudentsByCourseCommand() {
    return findStudentsByCourseCommand;
  }

  @Override
  public Command getAddStudentCommand() {
    return addStudentCommand;
  }

  @Override
  public Command getDeleteByIdCommand() {
    return deleteByIdCommand;
  }

  @Override
  public Command getAddToCourseCommand() {
    return addToCourseCommand;
  }

  @Override
  public Command getRemoveFromCourseCommand() {
    return removeFromCourseCommand;
  }

  @Override
  public Command getExitCommand() {
    return exitCommand;
  }

}
