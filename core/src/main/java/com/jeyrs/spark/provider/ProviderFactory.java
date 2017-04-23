package com.jeyrs.spark.provider;

import com.jeyrs.spark.model.Model;

public interface ProviderFactory<T extends Model> {
  DataProvider get(Class<T> clazz);
}
