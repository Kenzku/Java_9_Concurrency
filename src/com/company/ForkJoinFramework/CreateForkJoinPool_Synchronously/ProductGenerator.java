package com.company.ForkJoinFramework.CreateForkJoinPool_Synchronously;

import java.util.ArrayList;
import java.util.List;

public class ProductGenerator {

  public List<Product> generate(int size) {
    List<Product> productList = new ArrayList<Product>();

    for (int i = 0; i < size; i = i + 1 ) {
      Product product = new Product();

      product.setName("Product " + i);

      product.setPrice(10);

      productList.add(product);
    }
    return productList;
  }


}
