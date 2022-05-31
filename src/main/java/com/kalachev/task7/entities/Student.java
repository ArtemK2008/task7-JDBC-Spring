package com.kalachev.task7.entities;

public class Student {
  private int id;
  private int groupdId;
  private String firstName;
  private String lastName;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getGroupdId() {
    return groupdId;
  }

  public void setGroupdId(int groupdId) {
    this.groupdId = groupdId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
