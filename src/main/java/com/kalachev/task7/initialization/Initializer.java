package com.kalachev.task7.initialization;

import org.springframework.context.event.ContextRefreshedEvent;

public interface Initializer {

  void initializeTablesEvent(ContextRefreshedEvent event);

}