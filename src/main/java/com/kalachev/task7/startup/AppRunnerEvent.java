package com.kalachev.task7.startup;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kalachev.task7.Configuration.ConsoleAppEventConfig;
import com.kalachev.task7.ui.ConsoleMenuForEvent;

public class AppRunnerEvent {
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        ConsoleAppEventConfig.class);
    ((ConfigurableApplicationContext) context).start();

    ConsoleMenuForEvent menu = context.getBean("consoleMenuForEvent",
        ConsoleMenuForEvent.class);

    menu.runSchoolApp();
    ((ConfigurableApplicationContext) context).close();

  }
}
