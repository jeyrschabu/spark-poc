package com.jeyrs.spark.morphia.provider;

import com.jeyrs.spark.DatabaseConfig;
import com.jeyrs.spark.morphia.model.Product;

public class ProductProvider extends MorphiaProvider<Product> {
  public ProductProvider(DatabaseConfig config) {
    super(config, Product.class);
  }
}
