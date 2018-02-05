package com.company.ParallelAndReactiveStreams.FilteringElement;

import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.Person;
import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.PersonGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Huang, Fuguo (aka ken) on 05/02/2018.
 */
public class Main {

  public static void main(String[] args) {
    List<Person> persons= PersonGenerator.generatePersonList(10);

    // For each parallel
    System.out.print("********************************************************\n");
    System.out.print("Original List\n");
    persons.parallelStream().forEach(p -> {
      System.out.printf("%s, %s\n", p.getLastName(), p.getFirstName());
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    System.out.print("********************************************************\n");
    System.out.print("List Without Duplicates\n");
    persons.parallelStream().distinct().forEach(p -> {
      System.out.printf("%s, %s\n", p.getLastName(), p.getFirstName());
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    System.out.print("********************************************************\n");
    System.out.print("Array of Numbers Without Duplicates\n");
    Integer[] numbers = {1,3,3,4,4,5,4,2,1,3,4,6,7,6,7,8,6,5,8,1,1,3,5,6,8,9,10,10,22,10,11,22,34};
    Arrays.asList(numbers).parallelStream()./*convert to an int stream*/mapToInt(n -> n).distinct().forEach(number -> {
      System.out.printf("Number: %d\n", number);
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    System.out.print("********************************************************\n");
    System.out.print("Filtering People\n");
    persons.parallelStream().filter(person -> person.getSalary() < 30_000).forEach(p -> {
      System.out.printf("%s, %s\n", p.getLastName(), p.getFirstName());
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    System.out.print("********************************************************\n");
    System.out.print("Filtering Number\n");
    Arrays.asList(numbers).parallelStream()./*convert to an int stream*/mapToInt(n -> n).filter(number -> number < 2).forEach(number -> {
      System.out.printf("Number: %d\n", number);
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    System.out.print("********************************************************\n");
    System.out.print("Limiting Number\n");
    persons.parallelStream().mapToDouble(Person::getSalary).sorted().limit(5).forEach(salary -> {
      System.out.printf("Top 5 Salary: %f\n", salary);
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");
  }
}
