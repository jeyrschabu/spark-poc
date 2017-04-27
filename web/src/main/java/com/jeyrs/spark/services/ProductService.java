package com.jeyrs.spark.services;

import com.jeyrs.spark.model.Product;
import com.jeyrs.spark.morphia.model.MorphiaProduct;
import com.jeyrs.spark.morphia.provider.ProductProvider;
import java.util.List;

public class ProductService {
  private ProductProvider provider;
  public ProductService(ProductProvider provider) {
    this.provider = provider;
  }

  public Product create(Product product) {
    return provider.update(product);
  }

  public Product update(Product product) {
    return provider.update(product);
  }


  public List<Product> findAll() {
    return provider.findAll();
  }

  public Product find(String key, String value) {
    return provider.findOne(key, value);
  }

  public Product find(String id) {
    return provider.findById(id);
  }

  public boolean remove(String id) {
    return provider.delete(id);
  }

  public List<Product> findMany(String key, String value) {
    return provider.findMany(key, value);
  }
}
