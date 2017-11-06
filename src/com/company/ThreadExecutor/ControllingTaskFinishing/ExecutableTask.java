package com.company.ThreadExecutor.ControllingTaskFinishing;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ExecutableTask implements Callable<String> {
  private final String name;

  public ExecutableTask(String name) {
    this.name = name;
  }

  @Override
  public String call() throws Exception {
    Long duration = (long) (Math.random() * 10);
    System.out.printf("%s: Waiting %d seconds for results.\n", this.name, duration);
    TimeUnit.SECONDS.sleep(duration);
    return "Hello, world. This is " + name;
  }

  public String getName() {
    return name;
  }
}
