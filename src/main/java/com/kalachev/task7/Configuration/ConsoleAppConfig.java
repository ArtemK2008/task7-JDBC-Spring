
package com.kalachev.task7.Configuration;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.kalachev.task7.ComponentScanInterface;
import com.kalachev.task7.dao.implementations.CoursesDaoImpl;
import com.kalachev.task7.dao.implementations.GroupsDaoImpl;
import com.kalachev.task7.dao.implementations.StudentsDaoImpl;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.GroupsDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.initialization.CoursesInitializerImpl;
import com.kalachev.task7.initialization.GroupInitializerImpl;
import com.kalachev.task7.initialization.SchemaInitializerImpl;
import com.kalachev.task7.initialization.StudentInitializerImpl;
import com.kalachev.task7.initialization.initialization_interfaces.CoursesInitializer;
import com.kalachev.task7.initialization.initialization_interfaces.GroupInitializer;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.initialization.initialization_interfaces.SchemaInitializer;
import com.kalachev.task7.initialization.initialization_interfaces.StudentInitializer;
import com.kalachev.task7.initialization.spring.SpringInitializer;
import com.kalachev.task7.service.options.CoursesOptionsImpl;
import com.kalachev.task7.service.options.GroupOptionsImpl;
import com.kalachev.task7.service.options.StudentOptionsImpl;
import com.kalachev.task7.service.options_interfaces.CoursesOptions;
import com.kalachev.task7.service.options_interfaces.GroupOptions;
import com.kalachev.task7.service.options_interfaces.StudentOptions;
import com.kalachev.task7.ui.ConsoleMenuForSpring;
import com.kalachev.task7.ui.commands.AddStudentCommand;
import com.kalachev.task7.ui.commands.AddToCourseCommand;
import com.kalachev.task7.ui.commands.Command;
import com.kalachev.task7.ui.commands.DeleteByIdCommand;
import com.kalachev.task7.ui.commands.ExitCommand;
import com.kalachev.task7.ui.commands.FindStudentsByCourseCommand;
import com.kalachev.task7.ui.commands.GroupSizeCommand;
import com.kalachev.task7.ui.commands.RemoveFromCourseCommand;
import com.kalachev.task7.ui.dispatcher.CommandDispatcher;
import com.kalachev.task7.ui.dispatcher.CommandDispatcherImpl;

@Configuration
@ComponentScan(basePackageClasses = { ComponentScanInterface.class })
public class ConsoleAppConfig {

  @Bean
  public StudentInitializer studentInitializerImpl() {
    return new StudentInitializerImpl();
  }

  @Bean
  public CoursesInitializer coursesInitializerImpl() {
    return new CoursesInitializerImpl();
  }

  @Bean
  public GroupInitializer groupInitializerImpl() {
    return new GroupInitializerImpl();
  }

  @Bean
  public SchemaInitializer schemaInitializerImpl() {
    return new SchemaInitializerImpl();
  }

  @Bean
  public StudentsDao studentsDao() {
    return new StudentsDaoImpl();
  }

  @Bean
  public CoursesDao courseDao() {
    return new CoursesDaoImpl();
  }

  @Bean
  public GroupsDao groupsDao() {
    return new GroupsDaoImpl();
  }

  @Bean
  public GroupOptions groupOptionsImpl() {
    return new GroupOptionsImpl(groupsDao());
  }

  @Bean
  public StudentOptions studentOptionsImpl() {
    return new StudentOptionsImpl(studentsDao(), courseDao());
  }

  @Bean
  public CoursesOptions coursesOptionsImpl() {
    return new CoursesOptionsImpl(courseDao(), studentsDao());
  }

  @Bean
  public Scanner scanner() {
    return new Scanner(System.in);
  }

  @Bean
  public Command groupSizeCommand() {
    return new GroupSizeCommand(scanner(), groupOptionsImpl());
  }

  @Bean
  public Command findStudentsByCourseCommand() {
    return new FindStudentsByCourseCommand(scanner(), coursesOptionsImpl(),
        studentOptionsImpl());
  }

  @Bean
  public Command addStudentCommand() {
    return new AddStudentCommand(scanner(), studentOptionsImpl());
  }

  @Bean
  public Command deleteByIdCommand() {
    return new DeleteByIdCommand(scanner(), studentOptionsImpl());
  }

  @Bean
  public Command addToCourseCommand() {
    return new AddToCourseCommand(scanner(), coursesOptionsImpl());
  }

  @Bean
  public Command removeFromCourseCommand() {
    return new RemoveFromCourseCommand(scanner(), coursesOptionsImpl());
  }

  @Bean
  public Command exitCommand() {
    return new ExitCommand(scanner());
  }

  @Bean
  public CommandDispatcher commandDispatcher() {
    return new CommandDispatcherImpl(groupSizeCommand(),
        findStudentsByCourseCommand(), addStudentCommand(), deleteByIdCommand(),
        addToCourseCommand(), removeFromCourseCommand(), exitCommand());
  }

  @Bean
  public Initializer initializer() {
    return new SpringInitializer(studentInitializerImpl(),
        coursesInitializerImpl(), groupInitializerImpl(),
        schemaInitializerImpl());
  }

  @Bean
  public ConsoleMenuForSpring consoleMenu() {
    return new ConsoleMenuForSpring(scanner(), initializer(),
        commandDispatcher());
  }
}
