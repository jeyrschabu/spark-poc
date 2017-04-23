package com.jeyrs.spark.provider;

import com.jeyrs.spark.model.Model;

import java.util.List;

public interface DataProvider<T extends Model> {
  List<T> findAll();
  void insert(List<T> list);
  T findById(String id);
  T findOne(String key, String value);
  List<T>  findMany(String key, String value);
  boolean delete(String id);
  T update(T model);
}
