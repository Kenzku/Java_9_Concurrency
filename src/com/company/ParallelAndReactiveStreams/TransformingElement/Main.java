package com.company.ParallelAndReactiveStreams.TransformingElement;

import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.Person;
import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.PersonGenerator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {

    List<Person> persons = PersonGenerator.generatePersonList(10);

    // Map to double
    System.out.print("********************************************************\n");
    System.out.print("Transform from Int to Double\n");
    DoubleStream doubleStream = persons.parallelStream().mapToDouble(Person::getSalary);
    doubleStream.distinct().forEach(d -> {
      System.out.printf("Salary: %f\n", d);
    });
    doubleStream = persons.parallelStream().mapToDouble(Person::getSalary);
    long size = doubleStream.distinct().count();
    System.out.printf("Size: %d\n", size);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Map to object
    System.out.print("********************************************************\n");
    System.out.print("Transform from Person to Basic Person\n");
    List<BasicPerson> basicPersons = persons.parallelStream().map(person -> {
      BasicPerson basicPerson = new BasicPerson();
      basicPerson.setName(person.getFirstName() + " " + person.getLastName());
      basicPerson.setAge(getAge(person.getBirthDate()));
      return basicPerson;
    }).collect(Collectors.toList());

    basicPersons.forEach(basicPerson -> {
      System.out.printf("%s: %d\n", basicPerson.getName(), basicPerson.getAge());
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Flap Map
    System.out.print("********************************************************\n");
    List<String> file = FileGenerator.generateFile(100);
    Map<String, Long> wordCount = file.parallelStream().flatMap(line -> Stream.of(line.split("[ ,.]")))
        .filter(w -> w.length() > 0).sorted().collect(Collectors.groupingByConcurrent(e -> e, Collectors.counting()));

    wordCount.forEach((k, v) -> {
      System.out.printf("%s: %d\n", k, v);
    });
    System.out.print("********************************************************\n");
  }

  private static long getAge(Date birthDate) {
    LocalDate start = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate now = LocalDate.now();
    return ChronoUnit.YEARS.between(start, now);
  }

}
