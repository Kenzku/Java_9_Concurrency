package com.company.ForkJoinFramework.CancelTask;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Due to the limitation of Fork Join Pool,
 * it needs this class to cancel tasks that have not sent to the Fork Join Pool
 */
public class TaskManager {

  private final ConcurrentLinkedDeque<SearchNumberTask> tasks;

  public TaskManager() {
    tasks = new ConcurrentLinkedDeque<>();
  }

  public void addTask(SearchNumberTask task) {
    tasks.add(task);
  }

  public void cancelTasks(SearchNumberTask cancelTask) {
    for (SearchNumberTask task : tasks) {
      if (task != cancelTask) {
        // it will not cancel the task, if the task is already running. nothing to do with the true
        task.cancel(true);
        task.logCancelMessage();
      }
    }
  }
}
