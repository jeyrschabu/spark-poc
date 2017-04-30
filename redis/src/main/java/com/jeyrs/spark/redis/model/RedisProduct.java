package com.jeyrs.spark.redis.model;

import com.jeyrs.spark.model.Product;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RedisProduct implements Product {
  @Override
  public String getId() {
    return null;
  }

  @Override
  public void setId(String id) {

  }

  private String name;
  private String category;
  private Double price;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getGetCategory() {
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
}
