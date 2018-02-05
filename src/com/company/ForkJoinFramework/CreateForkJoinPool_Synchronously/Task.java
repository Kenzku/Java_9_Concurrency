package com.company.ForkJoinFramework.CreateForkJoinPool_Synchronously;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {

  private List<Product> productList;

  private int first;
  private int last;
  private double increment;

  public Task(List<Product> productList, int first, int last, double increment) {
    this.productList = productList;
    this.first = first;
    this.last = last;
    this.increment = increment;
  }

  @Override
  protected void compute() {
    if (last - first < 10) {
      updatePrices();
    } else {
      // if the list has more than 10 products, it divides the list into 2 lists handled by 2 tasks
      int middle = (last + first) / 2;
      System.out.printf("Task: Pending tasks: %s \n", getQueuedTaskCount());
      Task t1 = new Task(productList, first, middle + 2, increment);
      Task t2 = new Task(productList, middle + 1, last, increment);
      invokeAll(t1, t2);
    }
  }

  private void updatePrices() {
    for (int i = first; i < last; i = i +1) {
      Product product = productList.get(i);
      product.setPrice(product.getPrice() * (1 + increment));
    }
  }
}
