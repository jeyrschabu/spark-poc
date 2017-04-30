package com.jeyrs.spark.redis.model;

import com.jeyrs.spark.model.Product;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RedisProduct implements Product {
  private String id;
  private String name;
  private String category;
  private Double price;

  @Override
  public String getId() {
    return this.getName() + ":" + this.getCategory() + ":" + this.getPrice();
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getPrice() {
    return this.price;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public RedisProduct withCategory(String category) {
    this.setCategory(category);
    return this;
  }

  public RedisProduct withPrice(Double price) {
    setPrice(price);
    return this;
  }

  public RedisProduct withName(String name) {
    setName(name);
    return this;
  }
}
