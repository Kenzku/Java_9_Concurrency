package com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Created by Huang, Fuguo (aka ken) on 02/02/2018.
 */
public class Main {
  public static void main(String[] args) {
    // Creating a stream from a Collection
    System.out.print("********************************************************\n");
    System.out.print("From a Collection:\n");
    /* create a list of 10_000 persons */
    List<Person> personList = PersonGenerator.generatePersonList(10_000);
    Stream<Person> personStream = personList.parallelStream();
    System.out.printf("Number of persons: %d\n", personStream.count());
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Using a generator
    System.out.print("********************************************************\n");
    System.out.print("From a Supplier:\n");
    Supplier<String> supplier = new MySupplier();
    Stream<String> generatorStream = Stream.generate(supplier);
    generatorStream.parallel().limit(10).forEach(s -> System.out.printf("%s\n", s));
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // From predefined elements
    System.out.print("********************************************************\n");
    System.out.print("From a predefined set of elements:\n");
    Stream<String> elementsStream = Stream.of("Peter", "John", "Mary");
    elementsStream.parallel().forEach(element -> System.out.printf("%s\n", element));
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // From a File
    System.out.print("********************************************************\n");
    System.out.print("From a File:\n");
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/com/company/ParallelAndReactiveStreams/Data/nursery.data"));) {
      Stream<String> fileLines = bufferedReader.lines();
      System.out.printf("Number of lines in the file: %d\n\n", fileLines.parallel().count());
      System.out.print("********************************************************\n");
      System.out.print("\n");
      fileLines.close();
      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // From a directory
    System.out.print("********************************************************\n");
    System.out.print("From a Directory:\n");
    try {
      Stream<Path> directoryContent = Files.list(Paths.get(System.getProperty("user.home")));
      System.out.printf("Number of elements (files and folders):%d\n\n", directoryContent.parallel().count());
      /*close the stream*/
      directoryContent.close();
      System.out.print("********************************************************\n");
      System.out.print("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    // From an array
    System.out.print("********************************************************\n");
    System.out.print("From an Array:\n");
    String array[] = { "1", "2", "3", "4", "5" };
    Stream<String> streamFromArray = Arrays.stream(array);
    streamFromArray.parallel().forEach(s -> System.out.printf("%s : ", s));
    System.out.print("\n");
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Random numbers
    System.out.print("********************************************************\n");
    System.out.print("Random number generators:\n");
    Random random = new Random();
    DoubleStream doubleStream = random.doubles(10);
    /*the peek method to write each of the elements to the console, and return the same stream*/
    double doubleStreamAverage = doubleStream.parallel().peek(d -> System.out.printf("%f : ", d)).average().orElse(0);
    System.out.printf("\nDouble Stream Average: %f\n", doubleStreamAverage);
    System.out.print("********************************************************\n");
    System.out.print("\n");

    // Concatenating streams
    System.out.print("********************************************************\n");
    System.out.print("Concatenating streams:\n");
    Stream<String> stream1 = Stream.of("1", "2", "3", "4");
    Stream<String> stream2 = Stream.of("5", "6", "7", "8");
    Stream<String> finalStream = Stream.concat(stream1, stream2);
    finalStream.parallel().forEach(s -> System.out.printf("%s : ", s));
    System.out.print("\n");
    System.out.print("********************************************************\n");
    System.out.print("\n");
  }
}
