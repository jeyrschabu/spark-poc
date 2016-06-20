package com.jeyrs.spark.models;

import org.mongodb.morphia.annotations.Id;

public class Model {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
