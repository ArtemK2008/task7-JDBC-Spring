package com.kalachev.task7.ui.dispatcher;

import com.kalachev.task7.ui.commands.Command;

public interface CommandDispatcher {

  Command getGroupSizeCommand();

  Command getFindStudentsByCourseCommand();

  Command getAddStudentCommand();

  Command getDeleteByIdCommand();

  Command getAddToCourseCommand();

  Command getRemoveFromCourseCommand();

  Command getExitCommand();

}
