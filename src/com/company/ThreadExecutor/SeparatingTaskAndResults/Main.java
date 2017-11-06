package com.company.ThreadExecutor.SeparatingTaskAndResults;

import java.sql.Time;
import java.util.concurrent.*;

public class Main {
  public static void main(String[] args) {
    // Create the executor and the CompletionService using the executor
    ExecutorService executorService = Executors.newCachedThreadPool();

    // A CompletionService that uses a supplied Executor to execute tasks.
    CompletionService<String> service = new ExecutorCompletionService<>(executorService);

    // Create two ReportRequest object and executed by two threads
    ReportRequest faceRequest = new ReportRequest("Face", service);
    ReportRequest onlineRequest = new ReportRequest("Online", service);

    Thread faceThread = new Thread(faceRequest);
    Thread onlineThread = new Thread(onlineRequest);

    // Create a ReportProcessor object and a thread to execute it
    ReportProcessor processor = new ReportProcessor(service);
    Thread senderThread = new Thread(processor);

    // Start the Threads
    System.out.printf("Main: String the threads\n");

    /*The starting order does not matter, the Report process will wait until the report requests are all finished*/
    faceThread.start();
    onlineThread.start();
    senderThread.start(); /*processor*/

    // Wait for the end of the report generator tasks
    System.out.printf("Main: Waiting for the report generators. \n");
    try {
      // wait for the finalisation of the report requests threads
      faceThread.join();
      onlineThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // shutdown the executor
    System.out.printf("Main: Shutting down the executor. \n");
    // finish the executor and reject any further tasks to be submitted
    executorService.shutdown();

    try {
      // wait for the finalisation of all active threads
      executorService.awaitTermination(5, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    processor.stopProcessing();
    System.out.printf("Main: End.\n");
  }
}
