package com.company.ThreadExecutor.RuningTaskWithExecutorWithDelay;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Huang, Fuguo (aka ken) on 06/11/2017.
 */
public class Task implements Callable<String> {
  private String name;

  public Task(String name) {
    this.name = name;
  }

  @Override
  public String call() throws Exception {
    System.out.printf("%s: Starting at %s\n", name, new Date());
    long duration = (long) (Math.random() * 1000);
    TimeUnit.MILLISECONDS.sleep(duration);
    System.out.printf("%s: Finished at %s\n", name, new Date());
    return "Hello World";
  }
}
