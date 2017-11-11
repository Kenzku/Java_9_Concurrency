package com.company.ThreadExecutor.ExecutorWithReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Huang, Fuguo (aka ken) on 03/11/2017.
 */
public class Main {
  public static void main(String[] args) {
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    List<Future<Integer>> resultList = new ArrayList<>();

    Random random = new Random();

    for (int i = 0; i < 10; i = i + 1) {
      Integer number = random.nextInt(10);
      FactorialCalculator calculator = new FactorialCalculator(number);
      Future<Integer> result = executor.submit(calculator);

      resultList.add(result);
    }

    do {
      System.out.printf("Main: Number of Completed Tasks: %d\n", executor.getCompletedTaskCount());
      for (int i = 0; i < resultList.size(); i = i + 1) {
        Future<Integer> result = resultList.get(i);
        System.out.printf("Main: Task %d: %s\n", i, result.isDone());
      }
      try {
        TimeUnit.MICROSECONDS.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (executor.getCompletedTaskCount() < resultList.size());

    // Write the results

    System.out.println("Main: Results\n");
    int i = 0;
    for (Future<Integer> result : resultList) {
      Integer number = null;
      try {
        number = result.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }

      System.out.printf("Core: Task %d: %d\n", i, number);
      i = i + 1;
    }
    // otherwise the Java programme will never end
    executor.shutdown();
  }
}
