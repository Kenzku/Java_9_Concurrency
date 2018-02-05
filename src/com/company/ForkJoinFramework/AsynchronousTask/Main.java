package com.company.ForkJoinFramework.AsynchronousTask;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    ForkJoinPool pool = new ForkJoinPool();

    // Create 3 FolderProcessor tasks for three different folders
    FolderProcessor pictures = new FolderProcessor("/Users/____/Pictures/Masa", "jpg");
    FolderProcessor jsProject = new FolderProcessor("/Users/____/Documents/Projects", "js");
    FolderProcessor javaProject = new FolderProcessor("/Users/____/Documents/Projects", "java");

    // execute the task in the pool
    pool.execute(pictures);
    pool.execute(jsProject);
    pool.execute(javaProject);

    do {
      System.out.printf("======================================================\n");
      System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
      System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
      System.out.printf("Main: Task count: %d\n", pool.getQueuedTaskCount());
      System.out.printf("Main: Steal count: %d\n", pool.getStealCount());
      System.out.printf("Main: Done?: %b - %b - %b\n", pictures.isDone(), jsProject.isDone(), javaProject.isDone());
      System.out.printf("Main: Left: %d - %d - %d\n", pictures.getPendingCount(), jsProject.getPendingCount(), javaProject.getPendingCount());
      System.out.printf("======================================================\n");
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (!pictures.isDone() || !jsProject.isDone() || !javaProject.isDone());

    pool.shutdown();

    //Write the number of results calculated by each task
    List<String> results;

    results = pictures.getResultList();
    System.out.printf("Pictures: %d files found. \n", results.size());

    results = jsProject.getResultList();
    System.out.printf("Js files: %d files found. \n", results.size());

    results = javaProject.getResultList();
    System.out.printf("Java files: %d files found. \n", results.size());


  }
}
