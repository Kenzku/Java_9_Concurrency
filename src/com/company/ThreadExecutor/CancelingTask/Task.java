package com.company.ThreadExecutor.CancelingTask;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<String> {
  @Override
  public String call() throws Exception {
    while (true) {
      System.out.printf("Task: Test\n");
      TimeUnit.MILLISECONDS.sleep(100);
    }
  }
}
