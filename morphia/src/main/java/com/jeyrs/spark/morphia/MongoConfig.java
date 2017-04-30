package com.jeyrs.spark.morphia;

import com.jeyrs.spark.StorageConfig;

public class MongoConfig implements StorageConfig {
  public static final String MODELS_PACKAGE = "com.jeyrs.spark.morphia.models";
  private String database;
  private String username;
  private String password;
  private String host;

  public String getDatabase() {
    return database;
  }

  public MongoConfig withDatabase(String database) {
    this.database = database;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public MongoConfig withPassword(String password) {
    this.password = password;
    return this;
  }

  public String getHost() {
    return host;
  }

  public MongoConfig withHost(String host) {
    this.host = host;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public String getModelPackageName() {
    return MODELS_PACKAGE;
  }

  public MongoConfig withUsername(String username) {
    this.username = username;
    return this;
  }
}
