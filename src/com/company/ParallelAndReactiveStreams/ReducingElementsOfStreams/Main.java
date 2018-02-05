package com.company.ParallelAndReactiveStreams.ReducingElementsOfStreams;

import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.Person;
import com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources.PersonGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

public class Main {
  public static void main(String[] args) {

    System.out.print("********************************************************\n");
    System.out.print("Main: Examples of reduce methods.\n");

    // Working with streams of numbers
    System.out.print("Main: Creating a list of double numbers.\n");
    List<Double> numbers = DoubleGenerator.generateDoubleList(10000, 1000);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Getting the number of elements
    System.out.print("********************************************************\n");
    DoubleStream doubleStream = DoubleGenerator.generateStreamFromList(numbers);
    long numberOfElements = doubleStream.parallel().count();
    System.out.printf("The list of numbers has %d elements.\n", numberOfElements);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Getting the sum of the numbers
    System.out.print("********************************************************\n");
    doubleStream = DoubleGenerator.generateStreamFromList(numbers);
    double sum = doubleStream.parallel().sum();
    System.out.printf("Its numbers sum %f.\n", sum);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Getting the average of the numbers
    System.out.print("********************************************************\n");
    doubleStream = DoubleGenerator.generateStreamFromList(numbers);
    double average = doubleStream.parallel().average().orElse(0);
    System.out.printf("Its numbers have an average value of %f.\n", average);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Getting the highest value
    System.out.print("********************************************************\n");
    doubleStream = DoubleGenerator.generateStreamFromList(numbers);
    double max = doubleStream.parallel().max().orElse(0);
    System.out.printf("The maximum value in the list is %f.\n", max);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Getting the lowest value
    System.out.print("********************************************************\n");
    doubleStream = DoubleGenerator.generateStreamFromList(numbers);
    double min = doubleStream.parallel().min().orElse(0);
    System.out.printf("The minimum value in the list is %f.\n", min);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Reduce method. First version
    System.out.print("********************************************************\n");
    System.out.print("Reduce - First Version\n");
    List<Point> points = PointGenerator.generatePointList(10_000);
    Optional<Point> point = points.parallelStream().reduce((p1, p2) -> {
      Point p = new Point();
      p.setX(p1.getX() + p2.getX());
      p.setY(p1.getY() + p2.getY());
      return p;
    });
    System.out.println(point.get().getX() + ":" + point.get().getY());
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Reduce method. Second version
    System.out.print("********************************************************\n");
    System.out.print("Reduce, second version\n");
    List<Person> persons = PersonGenerator.generatePersonList(10000);

    long totalSalary = persons.parallelStream().map(Person::getSalary).reduce(0, (s1, s2) -> s1 + s2);
    System.out.printf("Total salary: %d\n", totalSalary);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Reduce method. Third version
    System.out.print("********************************************************\n");
    System.out.print("Reduce, third version\n");
    Integer value = 0;
    value = persons.parallelStream().reduce(value, (n, p) -> {
      if (p.getSalary() > 50000) {
        return n + 1;
      } else {
        return n;
      }
    }, (n1, n2) -> n1 + n2);
    System.out.printf("The number of people with a salary bigger that 50,000 is %d\n", value);
    System.out.print("********************************************************\n");
    System.out.print("\n");

  }

}
