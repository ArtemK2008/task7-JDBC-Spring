package com.kalachev.task7.service.options_interfaces;

import java.util.List;

public interface GroupOptions {

  List<String> findBySize(int maxSize);

}