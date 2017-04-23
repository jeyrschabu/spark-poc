package com.jeyrs.spark.model;

import org.mongodb.morphia.annotations.Id;

public class MorphiaModel implements Model {
  @Id
  private String id;

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }
}
