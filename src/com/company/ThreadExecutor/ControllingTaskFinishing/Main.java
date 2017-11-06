package com.company.ThreadExecutor.ControllingTaskFinishing;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();

    ResultTask resultTasks[] = new ResultTask[5];

    for (int i = 0; i < 5; i = i + 1) {
      ExecutableTask executableTask = new ExecutableTask("Task " + i);
      resultTasks[i] = new ResultTask(executableTask);
      executorService.submit(resultTasks[i]);
    }

    // Put the Main thread to sleep for 5 seoncds
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    for (ResultTask resultTask : resultTasks) {
      resultTask.cancel(true);
    }

    // Write the results of the tasks that have not been cancelled
    for (ResultTask resultTask : resultTasks) {
      try {
        if (!resultTask.isCancelled()) {
          System.out.printf("%s\n", resultTask.get());
        }
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    executorService.shutdown();
  }
}
