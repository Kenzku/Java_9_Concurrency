package com.company.ThreadExecutor.SeparatingTaskAndResults;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ReportGenerator implements Callable<String> {
  private final String sender;
  private final String title;

  public ReportGenerator(String sender, String title) {
    this.title = title;
    this.sender = sender;
  }

  @Override
  public String call() throws Exception {
    try {
      Long duration = (long) (Math.random() * 10);
      System.out.printf("%s_%s: Report Generator: Generating a report during %d seconds\n", this.sender, this.title, duration);
      TimeUnit.SECONDS.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return sender + ": " + title;
  }
}
