package com.kalachev.task7.dao.spring;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.dao.implementations.spring.GroupDaoSpring;
import com.kalachev.task7.dao.interfaces.GroupsDao;

class GroupDaoSpringTest extends DbUnitConfigSpring {

  GroupsDao groupsDao;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    groupsDao = new GroupDaoSpring(template);
    beforeData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/group/ActualGroupDataSet.xml").getFile()));
    databaseTester.setDataSet(beforeData);
    databaseTester.onSetup();
  }

  @Test
  void testFindBySize_shouldReturnGroupsWithAtLeastThreeStudent_whenCalledWithValidTables() {
    // given
    List<Group> expected = new LinkedList<>();
    Group group = new Group();
    group.setGroupName("aa-11");
    group.setId(1);
    expected.add(group);
    group = new Group();
    group.setGroupName("aa-22");
    group.setId(2);
    expected.add(group);
    // when
    List<Group> actual = groupsDao.findBySize(3);
    // then
    assertTrue(actual.size() == expected.size() && actual.containsAll(expected)
        && expected.containsAll(actual));
  }
}
