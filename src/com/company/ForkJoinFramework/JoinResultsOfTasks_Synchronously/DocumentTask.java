package com.company.ForkJoinFramework.JoinResultsOfTasks_Synchronously;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask<Integer>{

  private String document[][];
  private int startLine;
  private int endLine;
  private String word;

  public DocumentTask(String[][] document, int startLine, int endLine, String word) {
    this.document = document;
    this.startLine = startLine;
    this.endLine = endLine;
    this.word = word;
  }

  @Override
  protected Integer compute() {
    Integer result = null;

    // recursive condition
    if (endLine - startLine < 10) {
      result = processLines(document, startLine, endLine, word);
    } else {
      // recursively divides the into half and handled by 2 tasks
      int midLine = (startLine + endLine) / 2;
      DocumentTask task1 = new DocumentTask(document, startLine, midLine, word);
      DocumentTask task2 = new DocumentTask(document, midLine, endLine, word);
      invokeAll(task1, task2);

      try {
        result = groupResults(task1.get(), task2.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    return result;
  }

  private Integer groupResults(Integer integer, Integer integer1) {
    return integer + integer1;
  }

  private Integer processLines(String[][] document, int startWord, int endWord, String word) {
    List<LineTask> tasks = new ArrayList<>();

    for (int i = startWord; i < endWord; i = i + 1) {
      LineTask task = new LineTask(document[i], 0, document[i].length, word);
      tasks.add(task);
    }

    invokeAll(tasks);

    int result = 0;
    for (LineTask task : tasks) {
      try {
        result = result + task.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    return result;

  }


}
