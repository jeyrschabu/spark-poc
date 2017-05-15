package com.jeyrs.spark.services;

import com.jeyrs.spark.model.Product;
import com.jeyrs.spark.provider.DataProvider;

import java.util.List;
import java.util.UUID;

public class ProductService<T extends Product> {
  private DataProvider<T> provider;
  public ProductService(DataProvider<T> provider) {
    this.provider = provider;
  }

  public T create(T product) {
    product.setId(UUID.randomUUID().toString());
    return provider.update(product);
  }

  public T update(T product) {
    return provider.update(product);
  }


  public List<T> findAll() {
    return provider.findAll();
  }

  public T find(String key, String value) {
    return provider.findOne(key, value);
  }

  public T find(String id) {
    return provider.findById(id);
  }

  public boolean remove(String id) {
    return provider.delete(id);
  }

  public List<T> findMany(String key, String value) {
    return provider.findMany(key, value);
  }
}
