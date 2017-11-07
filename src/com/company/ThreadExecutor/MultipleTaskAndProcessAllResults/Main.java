package com.company.ThreadExecutor.MultipleTaskAndProcessAllResults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
  public static void main(String[] args) {
    // Create an executor
    ExecutorService executorService = (ExecutorService) Executors.newCachedThreadPool();

    // Create 10 tasks and stores them in a List
    List<Task> taskList = new ArrayList<>();
    for (int i = 0; i < 10; i ++) {
      Task task = new Task("Task-" + i);
      taskList.add(task);
    }

    List<Future<Result>> resultList = null;

    try {
      // Future invoke callable's call method and store the results returns from the callable into the future list
      resultList = executorService.invokeAll(taskList);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.printf("Main: Going to shutdown at: %s\n", new Date());
    // Terminate Executor, otherwise Java programme wont stop
    executorService.shutdown();
    System.out.printf("Main: shutdown at: %s\n", new Date());

    // Write the results to the console
    for (int i = 0; i < (resultList != null ? resultList.size() : 0); i = i + 1) {
      Future<Result> future = resultList.get(i);

      try {
        Result result = future.get();
        System.out.printf("%s: %s\n", result.getName(), result.getValue());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }
}
