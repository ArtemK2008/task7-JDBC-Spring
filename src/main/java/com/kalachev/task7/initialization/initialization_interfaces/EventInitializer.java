package com.kalachev.task7.initialization.initialization_interfaces;

import org.springframework.context.event.ContextStartedEvent;

public interface EventInitializer {

  void initializeTables(ContextStartedEvent event);

}