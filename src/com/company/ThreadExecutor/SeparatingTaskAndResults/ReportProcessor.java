package com.company.ThreadExecutor.SeparatingTaskAndResults;

import java.sql.Time;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ReportProcessor implements Runnable {
  private final CompletionService<String> service;

  /*The Java volatile keyword guarantees visibility of changes to variables across threads. */
  private volatile boolean end;

  public ReportProcessor(CompletionService<String> service) {
    this.service = service;
    this.end = false;
  }

  @Override
  public void run() {
    while (!end) {
      try {
        System.out.printf("processing....\n");
        /*Retrieves and removes the Future representing the next completed task, or null if none are present.*/
        Future<String> result = service.poll(20, TimeUnit.SECONDS);
        if (result != null) {
          String report = result.get();
          System.out.printf("ReportProcessor: Report Received: %s\n", report);
        }
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }

  public void stopProcessing() {
    this.end = true;
  }
}
