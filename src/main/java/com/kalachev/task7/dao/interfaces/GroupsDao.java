package com.kalachev.task7.dao.interfaces;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kalachev.task7.dao.entities.Group;

@Component
public interface GroupsDao {
  List<Group> findBySize(int size);
}