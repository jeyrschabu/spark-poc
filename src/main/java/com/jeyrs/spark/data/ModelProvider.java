package com.jeyrs.spark.data;

import com.jeyrs.spark.models.Model;

import java.util.List;

public interface ModelProvider<T extends Model> {
    List<T> findAll();
    void insert(List<T> list);
    T findById(String id);
    T findOne(String key, String value);
    List<T>  findMany(String key, String value);
    boolean delete(String id);
    T update(T model);
}
