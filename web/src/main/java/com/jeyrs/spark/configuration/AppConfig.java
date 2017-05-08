package com.jeyrs.spark.configuration;

import java.util.List;

public class AppConfig {
  private List<Database> databases;
  public List<Database> getDatabases() {
    return this.databases;
  }

  public void setDatabases(List<Database> databases) {
    this.databases = databases;
  }

  public static class Database {
    Boolean enabled;
    String name;
    String host;
    Integer port;
    String username;
    String password;
    String type;

    public Boolean getEnabled() {
      return enabled;
    }

    public void setEnabled(Boolean enabled) {
      this.enabled = enabled;
    }

    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }

    public Integer getPort() {
      return port;
    }

    public void setPort(Integer port) {
      this.port = port;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
