package com.company.ForkJoinFramework.AsynchronousTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountedCompleter;

public class FolderProcessor extends CountedCompleter<List<String>>{

  private String path;
  private String extension;

  private List<FolderProcessor> tasks;
  private List<String> resultList;

  /**
   * use internally
   * @param completer
   * @param path
   * @param extension
   */
  protected FolderProcessor(CountedCompleter<?> completer, String path, String extension) {
    super(completer);
    this.path = path;
    this.extension = extension;
  }

  public FolderProcessor(String path, String extension) {
    this.path = path;
    this.extension = extension;
  }

  @Override
  public void compute() {
    resultList = new ArrayList<>();
    tasks = new ArrayList<>();

    File file = new File(path);
    File content[] = file.listFiles();

    if (content != null) {
      for (File aContent : content) {
        if (aContent.isDirectory()) {
          /*If is a directory, process it. Execute a new task*/
          FolderProcessor task = new FolderProcessor(this, aContent.getAbsolutePath(), extension);
          // execute the task asynchronously using fork()
          task.fork();
          addToPendingCount(1);
          tasks.add(task);
        } else {
          // If it is a file, process it.
          // Compare the extension of the file and the extension that it is looking for by using checkFile method
          if (checkFile(aContent.getName())) {
            resultList.add(aContent.getAbsolutePath());
            System.out.printf("Result found: %s \n", aContent.getAbsolutePath());
          }
        }
        if (tasks.size() > 50) {
          System.out.printf("%s: %d Tasks ran. \n", file.getAbsolutePath(), tasks.size());
        }
      }
    }
    tryComplete();

  }

  @Override
  public void onCompletion(CountedCompleter<?> caller) {
    for (FolderProcessor childTask : tasks) {
      // add the results of the child tasks to the final result list
      resultList.addAll(childTask.getResultList());
    }
  }

  public List<String> getResultList() {
    return resultList;
  }

  private boolean checkFile(String name) {
    return name.endsWith(extension);
  }

}
