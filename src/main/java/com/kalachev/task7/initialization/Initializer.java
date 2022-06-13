package com.kalachev.task7.initialization;

import org.springframework.stereotype.Component;

@Component
public interface Initializer {

  void initializeTables();

}