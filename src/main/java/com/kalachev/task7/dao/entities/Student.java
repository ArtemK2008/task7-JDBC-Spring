package com.kalachev.task7.dao.entities;

import java.util.Objects;

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

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Student other = (Student) obj;
    return id == other.id;
  }

  @Override
  public String toString() {
    return "Student [id=" + id + ", groupdId=" + groupdId + ", firstName="
        + firstName + ", lastName=" + lastName + "]";
  }

}
