package com.company.ParallelAndReactiveStreams.CreateStreamsFromDifferentSources;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Created by Huang, Fuguo (aka ken) on 02/02/2018.
 */
public class MySupplier implements Supplier<String> {
  private final AtomicInteger counter;

  public MySupplier() {
    counter = new AtomicInteger(0);
  }

  @Override
  public String get() {
    int value = counter.getAndAdd(1);
    return "String " + value;
  }
}
