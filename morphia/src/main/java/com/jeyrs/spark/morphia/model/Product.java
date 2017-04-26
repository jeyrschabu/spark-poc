package com.jeyrs.spark.morphia.model;

import com.jeyrs.spark.model.BaseProduct;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("products")
public class Product implements BaseProduct {
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
