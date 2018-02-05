package com.company.ParallelAndReactiveStreams.ReducingElementsOfStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PointGenerator {

  public static List<Point> generatePointList(int size) {
    List<Point> pointArrayList = new ArrayList<>();

    Random randomGenerator = new Random();
    for (int i = 0; i < size; i++) {
      Point point = new Point();
      point.setX(randomGenerator.nextDouble());
      point.setY(randomGenerator.nextDouble());
      pointArrayList.add(point);
    }

    return pointArrayList;
  }
}