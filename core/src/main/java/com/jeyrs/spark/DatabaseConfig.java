package com.jeyrs.spark;

public class DatabaseConfig {
  String database;
  String username;
  String password;
  String host;

  public String getDatabase() {
    return database;
  }

  public DatabaseConfig withDatabase(String database) {
    this.database = database;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public DatabaseConfig withPassword(String password) {
    this.password = password;
    return this;
  }

  public String getHost() {
    return host;
  }

  public DatabaseConfig withHost(String host) {
    this.host = host;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public DatabaseConfig withUsername(String username) {
    this.username = username;
    return this;
  }
}
