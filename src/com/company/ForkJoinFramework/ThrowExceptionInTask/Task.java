package com.company.ForkJoinFramework.ThrowExceptionInTask;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class Task extends RecursiveTask<Integer> {
  private int array[];

  // start and end define the size of the task, used in RecursiveTask
  private int start;

  private int end;

  public Task(int[] array, int start, int end) {
    this.array = array;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    System.out.printf("Task: Start from %d to %d\n", start, end);

    // if the task has a size less than 10.
    if (end - start < 10) {
      // mock up exception
      if (start < 3 && 3 < end) {
        throw new RuntimeException("This task throws an Exception: Task from " + start + " to " + end);
      }
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      int mid = (end + start) / 2;
      // divide a task into 2 tasks recursively.
      Task task1 = new Task(array, start, mid);
      Task task2 = new Task(array, mid, end);
      // execute them in the pool
      invokeAll(task1, task2);
      // take for task 1 ends: task1.join()
      System.out.printf("Task: Result from %d to %d: %d\n", start, mid, task1.join());
      System.out.printf("Task: Result from %d to %d: %d\n", mid, end, task2.join());
    }
    System.out.printf("Task: End from %d to %d\n", start, end);

    return 0;
  }
}
