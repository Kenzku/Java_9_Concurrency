package com.company.ThreadExecutor.RejectedTaskController;


/**
 * Created by Huang, Fuguo (aka ken) on 03/11/2017.
 */
public class Main {
  public static void main(String[] args) {
    // Create the server
    Server server = new Server();

    // send 100 request to the server and finish
    System.out.printf("Main: Starting. \n");
    for (int i = 0; i < 10; i = i + 1) {
      Task task = new Task("Task " + i);
      server.executeTask(task);
    }

    System.out.printf("Main: Shutting down the Executor. \n");
    server.endServer();

    System.out.printf("Main: Sending another task. \n");
    Task task = new Task("Task after shutting down");
    server.executeTask(task);

    System.out.printf("Main: End. \n");
  }
}
