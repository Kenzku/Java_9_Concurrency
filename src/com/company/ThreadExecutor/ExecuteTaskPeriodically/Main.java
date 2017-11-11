package com.company.ThreadExecutor.ExecuteTaskPeriodically;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Huang, Fuguo (aka ken) on 06/11/2017.
 */
public class Main {
  public static void main(String[] args) {

    // Create a ScheduledThreadPoolExecutor
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    System.out.printf("Main: Starting at: %s\n", new Date());

    Task task = new Task("Task");

    // Send the task to the executor; with ? for non-parameterised return, as runnable is not parameterised
    ScheduledFuture<?> result = executorService.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);

    for (int i = 0; i < 10; i = i + 1) {
      System.out.printf("Main: Delay: %d\n", result.getDelay(TimeUnit.MILLISECONDS));

      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    executorService.shutdown();

    // Verify that the periodic tasks are not in execution after the executor shutdown
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Main: Finished at: %s\n", new Date());
  }

}
