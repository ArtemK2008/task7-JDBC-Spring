package com.kalachev.task7.startup;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kalachev.task7.Configuration.ConsoleAppConfig;
import com.kalachev.task7.ui.ConsoleMenuForSpring;

public class AppRunnerSpring {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        ConsoleAppConfig.class);
    ConsoleMenuForSpring obj = (ConsoleMenuForSpring) context
        .getBean("consoleMenu");

    BasicDataSource dataSourse = (BasicDataSource) context
        .getBean("dataSource");

    obj.runSchoolApp();
    ((ConfigurableApplicationContext) context).close();

  }
}
