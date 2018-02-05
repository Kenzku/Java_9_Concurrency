package com.company.ForkJoinFramework.CancelTask;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class SearchNumberTask extends RecursiveTask<Integer> {
  private int numbers[];

  private int start;

  private int end;

  private int number;

  private TaskManager manager;

  private final static int NOT_FOUND = -1;

  public SearchNumberTask(int[] numbers, int start, int end, int number, TaskManager manager) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
    this.number = number;
    this.manager = manager;
  }

  public void logCancelMessage() {
    System.out.printf("Task: Canceled task from %d to %d\n", start, end);
  }

  @Override
  protected Integer compute() {
    System.out.println("Task: " + start + "-" + end);

    int result;

    if (end - start > 10) {
      result = launchTask();
    } else {
      result = lookForNumber();
    }

    return result;
  }

  /**
   * put tasks into task manager first, then execute them
   * but as the ForkJoinPool tasks are ran asynchronously, not all tasks added are ran at the same time,
   * this leaves room to cancel
   * @return
   */
  private int launchTask() {
    int mid = (start + end) / 2;

    SearchNumberTask task1 = new SearchNumberTask(numbers, start, mid, number, manager);
    SearchNumberTask task2 = new SearchNumberTask(numbers, mid, end, number, manager);

    manager.addTask(task1);
    manager.addTask(task2);

    // execute the 2 tasks async with fork methods this will go to compute method, which triggers the recursive method
    task1.fork();
    task2.fork();

    int result;

    // Returns the result of the computation when it is done
    result = task1.join();
    if (result != -1) {
      return result;
    }

    result = task2.join();
    return result;
  }

  private int lookForNumber() {
    for (int i = start; i < end; i = i + 1) {
      if (numbers[i] == number) {
        System.out.printf("Task: Number %d found in position %d\n", number, i);
        manager.cancelTasks(this);
        return i;
      }
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return NOT_FOUND;
  }
}
