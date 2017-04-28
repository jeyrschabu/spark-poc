package com.jeyrs.spark.morphia.provider;

import com.jeyrs.spark.StorageConfig;
import com.jeyrs.spark.model.Product;

public class ProductProvider extends MorphiaProvider<Product> {
  public ProductProvider(StorageConfig config) {
    super(config, Product.class);
  }
}
