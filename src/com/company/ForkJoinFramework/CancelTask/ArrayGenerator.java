package com.company.ForkJoinFramework.CancelTask;

import java.util.Random;

public class ArrayGenerator {

  public int[] generateArray(int size) {
    int array[] = new int[size];
    Random random = new Random();
    for (int i = 0; i < size ; i = i + 1) {
      array[i] = random.nextInt(10);
    }
    return array;
  }
}
