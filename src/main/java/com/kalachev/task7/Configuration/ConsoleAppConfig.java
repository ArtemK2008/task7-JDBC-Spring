
package com.kalachev.task7.Configuration;

import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.kalachev.task7.ComponentScanInterface;
import com.kalachev.task7.dao.implementations.spring.CourseDaoSpring;
import com.kalachev.task7.dao.implementations.spring.GroupDaoSpring;
import com.kalachev.task7.dao.implementations.spring.StudentDaoSpring;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.GroupsDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.initialization.core.CoursesInitializerImpl;
import com.kalachev.task7.initialization.core.GroupInitializerImpl;
import com.kalachev.task7.initialization.core.SchemaInitializerImpl;
import com.kalachev.task7.initialization.core.StudentInitializerImpl;
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
@PropertySource("classpath:DbProperties")
public class ConsoleAppConfig {

  @Autowired
  Environment env;

  @Bean
  public BasicDataSource dataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName(env.getProperty("JDBC_DRIVER"));
    ds.setUrl(env.getProperty("URL"));
    ds.setUsername(env.getProperty("NAME"));
    ds.setPassword(env.getProperty("PASSWORD"));
    ds.setInitialSize(5);
    ds.setMaxTotal(10);
    return ds;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

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
    return new StudentDaoSpring(jdbcTemplate());
  }

  @Bean
  public CoursesDao courseDao() {
    return new CourseDaoSpring(jdbcTemplate());
  }

  @Bean
  public GroupsDao groupsDao() {
    return new GroupDaoSpring(jdbcTemplate());
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
