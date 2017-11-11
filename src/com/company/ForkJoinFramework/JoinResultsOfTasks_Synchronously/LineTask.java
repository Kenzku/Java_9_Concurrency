package com.company.ForkJoinFramework.JoinResultsOfTasks_Synchronously;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class LineTask extends RecursiveTask<Integer> {

  private String line[];
  private int startOrderNumberOfTheLine;
  private int endOrderNumberOfTheLine;
  private String word;

  public LineTask(String[] line, int startOrderNumberOfTheLine, int endOrderNumberOfTheLine, String word) {
    this.line = line;
    this.startOrderNumberOfTheLine = startOrderNumberOfTheLine;
    this.endOrderNumberOfTheLine = endOrderNumberOfTheLine;
    this.word = word;
  }

  @Override
  protected Integer compute() {
    Integer result = null;

    if (endOrderNumberOfTheLine - startOrderNumberOfTheLine < 100) {
      // if the line is less than 100, perform count the word between startOrderNumberOfTheLine and endOrderNumberOfTheLine
      result = count(line, startOrderNumberOfTheLine, endOrderNumberOfTheLine, word);
    } else {
      // create 2 tasks handling each half of the line.
      int mid = (startOrderNumberOfTheLine + endOrderNumberOfTheLine) / 2;
      LineTask task1 = new LineTask(line, startOrderNumberOfTheLine, mid, word);
      LineTask task2 = new LineTask(line, mid, endOrderNumberOfTheLine, word);
      // execute the 2 tasks
      invokeAll(task1, task2);
      try {
        // task1.get(): Waits if necessary for the computation (i.e. the compute() method) to complete, and then retrieves its result i.e. its return value
        result = groupResults(task1.get(), task2.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    return result;
  }

  private Integer groupResults(int i, int i1) {
    return i + i1;
  }

  private Integer count(String[] line, int startOrderNumberOfTheLine, int endOrderNumberOfTheLine, String word) {
    int counter = 0;
    for (int i = startOrderNumberOfTheLine; i < endOrderNumberOfTheLine; i = i + 1) {
      if (line[i].equals(word)) {
        counter = counter + 1;
      }
    }
    try {
      // simulation to slow down the task
      TimeUnit.MILLISECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return counter;
  }
}
