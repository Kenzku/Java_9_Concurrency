package com.company.ThreadExecutor.RuningTaskWithExecutorWithDelay;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Huang, Fuguo (aka ken) on 06/11/2017.
 */
public class Main {
  public static void main(String[] args) {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    System.out.printf("Main: Starting at %s\n", new Date());

    for (int i = 0; i < 2; i = i + 1) {
      Task task = new Task("Task " + i);
      executorService.schedule(task, i + 1, TimeUnit.SECONDS);
    }

    System.out.printf("Main: going to shutdown at: %s\n", new Date());
    executorService.shutdown();

    // Wait for the finalisation of the executor
    try {
      executorService.awaitTermination(1, TimeUnit.DAYS);
      System.out.printf("Main: shutdown at: %s\n", new Date());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Writes the finalisation message
    System.out.printf("Core: Ends at: %s", new Date());
  }
}
