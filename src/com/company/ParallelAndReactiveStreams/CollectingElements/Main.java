package com.company.ParallelAndReactiveStreams.CollectingElements;

import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.Person;
import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.PersonGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;


public class Main {
  public static void main(String[] args) {

    System.out.print("********************************************************\n");
    System.out.print("Main: Examples of collect methods.\n");

    // Generating a list of persons
    List<Person> persons = PersonGenerator.generatePersonList(10);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Collectors.groupingByConcurrent.
    System.out.print("********************************************************\n");
    System.out.print("Grouping By Concurrent\n");
    System.out.printf("Concurrent: %b\n", Collectors.groupingByConcurrent(p -> p).characteristics().contains(Characteristics.CONCURRENT));
    Map<String, List<Person>> personsByName = persons.parallelStream().collect(Collectors.groupingByConcurrent(Person::getFirstName));
    personsByName.keySet().forEach(key -> {
      List<Person> listOfPersons = personsByName.get(key);
      System.out.printf("%s: There are %d persons with that name\n", key, listOfPersons.size());
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Collectors.joining
    System.out.print("********************************************************\n");
    System.out.print("Joining\n");
    System.out.printf("Concurrent: %b\n", Collectors.joining().characteristics().contains(Characteristics.CONCURRENT));
    String message = persons.parallelStream().map(Person::toString).collect(Collectors.joining(","));
    System.out.printf("%s\n", message);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Collectors.partitionBy
    System.out.print("********************************************************\n");
    System.out.print("Partitioning By\n");
    System.out.printf("Concurrent: %s\n", Collectors.partitioningBy(p -> true).characteristics().contains(Characteristics.CONCURRENT));
    Map<Boolean, List<Person>> personsBySalary = persons.parallelStream().collect(Collectors.partitioningBy(p -> p.getSalary() > 50000));
    personsBySalary.keySet().forEach(key -> {
      List<Person> listOfPersons = personsBySalary.get(key);
      System.out.printf("%s: %d \n", key, listOfPersons.size());
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Collectors.toConcurrentMap
    System.out.print("********************************************************\n");
    System.out.print("To Concurrent Map\n");
    System.out.printf("Concurrent: %b\n", Collectors.toConcurrentMap(p -> p, p -> p).characteristics().contains(Characteristics.CONCURRENT));
    ConcurrentMap<String, String> nameMap = persons.parallelStream().collect(Collectors.toConcurrentMap(Person::getFirstName, Person::getLastName, (s1, s2) -> s1 + ", " + s2));
    nameMap.forEach((key, value) -> {
      System.out.printf("%s: %s \n", key, value);
    });
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Collect, first example
    System.out.print("********************************************************\n");
    System.out.print("Collect, first example\n");
    List<Person> highSalaryPeople = persons.parallelStream().collect(
        () -> new ArrayList<Person>(),
        (list, person) -> {
          if (person.getSalary() > 50000) {
            list.add(person);
          }
        },
        (people, c) -> people.addAll(c) /*used by parallestream*/
    );
    System.out.printf("High Salary People: %d\n", highSalaryPeople.size());
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Collect, second example
    System.out.print("********************************************************\n");
    System.out.print("Collect, second example\n");
    ConcurrentHashMap<String, Counter> peopleNames = persons.parallelStream().collect(
        ConcurrentHashMap::new,
        (hashMap, person) -> {
          hashMap.computeIfPresent(person.getFirstName(), (name, counter) -> {
            counter.increment();
            return counter;
          });
          hashMap.computeIfAbsent(person.getFirstName(), name -> {
            Counter c = new Counter();
            c.setValue(name);
            return c;
          });
        },
        /*the combiner is used by parallel stream*/
        (hashMap1, hashMap2) -> {
          hashMap2.forEach(10, (key, value) -> {
            hashMap1.merge(key, value, (v1, v2) -> {
              v1.setCounter(v1.getCounter() + v2.getCounter());
              return v1;
            });
          });
        }
    );

    peopleNames.forEach((name, counter) -> {
      System.out.printf("%s: %d\n", name, counter.getCounter());
    });

    System.out.print("********************************************************\n");
    System.out.print("\n");


  }

}
