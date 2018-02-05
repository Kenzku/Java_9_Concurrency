package com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources;

import java.util.Date;

/**
 * Created by Huang, Fuguo (aka ken) on 02/02/2018.
 */
public class Person implements Comparable<Person> {

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public double getCoeficient() {
    return coeficient;
  }

  public void setCoeficient(double coeficient) {
    this.coeficient = coeficient;
  }

  private int id;
  private String firstName;
  private String lastName;
  private Date birthDate;
  private int salary;
  private double coeficient;

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }

  @Override
  public int compareTo(Person otherPerson) {
    int compareLastNames = this.getLastName().compareTo(otherPerson.getLastName());
    if (compareLastNames != 0) {
      return compareLastNames;
    } else {
      return this.getFirstName().compareTo(otherPerson.getFirstName());
    }
  }
}
