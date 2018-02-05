package com.company.ForkJoinFramework.ThrowExceptionInTask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    int array[] = new int[100];

    Task task = new Task(array, 0, 100);

    ForkJoinPool pool = new ForkJoinPool();

    pool.execute(task);

    pool.shutdown();

    try {
      pool.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // check if the task, or one of its sub tasks has exceptions
    if (task.isCompletedAbnormally()) {
      System.out.printf("Main: An exceptino has occurred\n");
      System.out.printf("Main: %s\n", task.getException());
      System.out.println("========================================================================");
    }

    // task.join will get results and in this case will get the exception. Thus, you won't see Main: Result: 0
    System.out.printf("Main: Result: %d", task.join());
  }
}
