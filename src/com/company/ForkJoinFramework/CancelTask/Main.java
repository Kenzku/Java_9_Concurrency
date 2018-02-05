package com.company.ForkJoinFramework.CancelTask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    // Generate an array of 1000 integers
    ArrayGenerator generator = new ArrayGenerator();
    int array[] = generator.generateArray(1000);

    // Create a TaskManager object
    TaskManager manager = new TaskManager();

    // Create a ForkJoinPool with the default constructor
    ForkJoinPool pool = new ForkJoinPool();

    // Create a Task to process the array: 5 is the number you want to find and want to cancel
    SearchNumberTask task = new SearchNumberTask(array, 0, 1000, 5, manager);

    // Execute the task, as the SearchNumberTask is a ForkJoinPool task, thus, it will be handled as async
    pool.execute(task);

    // Shutdown the pool
    pool.shutdown();

    // Wait for the finalisation of the task
    try {
      pool.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Write a message to indicate the end of the programme
    System.out.printf("Main: The programme has finished\n");
  }
}
