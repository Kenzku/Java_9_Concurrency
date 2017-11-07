package com.company.ThreadExecutor.RejectedTaskController;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Huang, Fuguo (aka ken) on 03/11/2017.
 */
public class Server {

  private final ThreadPoolExecutor executor;

  public Server() {
    // Create the executor
    executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Create the controller for the rejected Tasks
    RejectedTaskController controller = new RejectedTaskController();

    // Establish the rejected task controller
    executor.setRejectedExecutionHandler(controller);
  }

  public void executeTask(Task task) {
    System.out.println("Server: A new task has arrived\n");
    executor.execute(task); // send task to Runnable for execution: send object task of that implemented the Runnable interface
    System.out.printf("Server: Pool Size: %d\n", executor.getPoolSize());
    System.out.printf("Server: Active Count: %d\n", executor.getActiveCount());
    System.out.printf("Server: Task Count: %d\n", executor.getTaskCount());
    System.out.printf("Server: Completed Tasks Count: %d\n", executor.getCompletedTaskCount());
  }

  public void endServer() {
    // if we do not turn off the executor, the Java programme will never end
    executor.shutdown();
  }
}
