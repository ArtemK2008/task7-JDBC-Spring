package com.kalachev.task7.initialization.initialization_interfaces;

import org.springframework.context.event.ContextRefreshedEvent;

public interface EventInitializer {

  void initializeTables(ContextRefreshedEvent event);

}