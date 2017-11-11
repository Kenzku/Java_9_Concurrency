package com.company.ForkJoinFramework.CreateForkJoinPool_Synchronously;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    // Create a list of products

    ProductGenerator generator = new ProductGenerator();
    List<Product> products = generator.generate(10000);

    Task task = new Task(products, 0, products.size(), 0.20);

    ForkJoinPool pool = new ForkJoinPool();

    // execute with default configuration
    pool.execute(task);

    // write information about the pool
    do {
      System.out.printf("------------------------------------------------------------\n");
      System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
      System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
      System.out.printf("Main: Task count: %d\n", pool.getQueuedTaskCount());
      System.out.printf("Main: Steal count: %d\n", pool.getStealCount());
      System.out.printf("------------------------------------------------------------\n");
      try {
        TimeUnit.MILLISECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (!task.isDone());

    // shutdown the pool
    pool.shutdown();


    // Check if the task has completed normally
    if (task.isCompletedNormally()) {
      System.out.printf("Main: The process has completed normally. \n");
    }

    // Expected result: 12. Write productes which price is not 12
    for (Product product : products) {
      if (product.getPrice() != 12) {
        System.out.printf("Prodcut %s: %f\n", product.getName(), product.getPrice());
      }
    }

    System.out.printf("Main: End of the programme.");
  }
}
