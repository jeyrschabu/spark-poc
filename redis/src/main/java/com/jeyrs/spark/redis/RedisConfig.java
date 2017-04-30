package com.jeyrs.spark.redis;

import com.jeyrs.spark.StorageConfig;

public class RedisConfig implements StorageConfig {
  private String connection;

  public String getConnection() {
    return this.connection;
  }

  public void setConnection(String connection) {
    this.connection = connection;
  }

  public String getModelPackageName() {
    return null; //NO-OP
  }

  public RedisConfig withConnection(String connection) {
    this.connection = connection;
    return this;
  }
}
