package com.kalachev.task7.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.kalachev.task7.service.data.GroupCreator;

class TestGroupCreater {

  private static final String GROUPLESS = "students without groups";
  List<String> expected = Arrays.asList("ra-73", "my-46", "wk-93", "nf-24",
      "ge-69", "eo-09", "ez-83", "zs-54", "qc-35", "tg-54", GROUPLESS);

  @Test
  void testCreateGroups_shouldCreateElevenDifferentGroups_whenMethodIsCalled() {
    GroupCreator groupCreator = new GroupCreator(1);
    List<String> groups = groupCreator.generateGroups();
    assertEquals(11, groups.size());
    assertTrue(groups.contains(GROUPLESS));
    assertEquals(expected, groups);
  }

  @Test
  void testCreateGroups_shouldCreateUniqueGroupsOnly_whenMethodIsCalledLotOfTimes() {
    GroupCreator groupCreator = new GroupCreator();
    boolean isRightSize = true;
    for (int i = 0; i < 1000; i++) {
      List<String> groups = groupCreator.generateGroups();
      Set<String> uniqGrousp = new HashSet<String>(groups);
      if (uniqGrousp.size() != 11) {
        isRightSize = false;
      }
    }
    assertTrue(isRightSize);
  }
}
