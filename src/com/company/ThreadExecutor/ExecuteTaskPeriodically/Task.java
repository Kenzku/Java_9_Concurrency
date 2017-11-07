package com.company.ThreadExecutor.ExecuteTaskPeriodically;

import java.util.Date;

/**
 * Created by Huang, Fuguo (aka ken) on 06/11/2017.
 */
public class Task implements Runnable {
  private final String name;

  public Task(String name) {
    this.name = name;
  }
  @Override
  public void run() {
    System.out.printf("%s: Executed at %s\n", name, new Date());
  }
}
