package com.kalachev.task7.dao;

import java.io.FileInputStream;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.dao.implementations.DaoChecksImpl;
import com.kalachev.task7.dao.interfaces.DaoChecks;
import com.kalachev.task7.exceptions.DaoException;

public class DaoChecksTest extends DbUnitConfig {
  DaoChecks daoCheck = new DaoChecksImpl();

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    beforeData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/checks/ActualCheckDataSet.xml").getFile()));
    databaseTester.setDataSet(beforeData);
    databaseTester.onSetup();
  }

  @Test
  void testcheckIfStudentInCourse_shouldReturnTrue_whenStudentInCourse()
      throws DaoException {
    boolean check = daoCheck.checkIfStudentInCourse(1, "Russian");
    assertTrue(check);
  }

  @Test
  void testcheckIfStudentInCourse_shouldReturnFalse_whenStudentNotInCourse()
      throws DaoException {
    boolean check = daoCheck.checkIfStudentInCourse(1, "Hindi");
    assertFalse(check);
  }

}
