package com.company.ThreadExecutor.ExecutorWithReturn;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Huang, Fuguo (aka ken) on 03/11/2017.
 */
public class FactorialCalculator implements Callable<Integer>{

  private final Integer number;

  public FactorialCalculator(Integer number) {
    this.number = number;
  }

  @Override
  public Integer call() throws Exception {
    int result = 1;

    // If the number is 0 or 1, return 1
    if( (number == 0) || (number == 1)) {
      result = 1;
    } else {
      // Else, calculate the factorial
      for (int i = 2; i <= number; i = i + 1) {
        result *= i;
        TimeUnit.MICROSECONDS.sleep(20);
      }
    }
    System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);

    return result;
  }
}
