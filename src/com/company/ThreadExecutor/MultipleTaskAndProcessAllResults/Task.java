package com.company.ThreadExecutor.MultipleTaskAndProcessAllResults;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<Result> {
  public Task(String name) {
    this.name = name;
  }

  private final String name;


  @Override
  public Result call() throws Exception {
    System.out.printf("%s: Starting\n", this.name);

    try {
      // this returns in seconds
      Long duration = (long) (Math.random() * 10);
      System.out.printf("%s: Waiting %d seconds for results. \n", this.name, duration);
      TimeUnit.SECONDS.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    int value = 0;

    for (int i = 0; i < 5; i = i + 1) {
      value += (int) (Math.random() * 100);
    }

    // Create the object with results
    Result result = new Result();
    result.setName(this.name);
    result.setValue(value);
    System.out.printf("%s: Ends\n", this.name);

    return result;
  }
}
