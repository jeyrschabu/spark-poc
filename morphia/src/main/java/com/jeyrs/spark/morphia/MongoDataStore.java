package com.jeyrs.spark.morphia;

import com.jeyrs.spark.DataStore;
import com.jeyrs.spark.DatabaseConfig;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

public class MongoDataStore implements DataStore {
  private MongoClient mongoClient;
  public MongoDataStore(DatabaseConfig config) {
    MongoClient mongoClient = new MongoClient();

    if (StringUtils.isNotEmpty(config.getDatabase()) && StringUtils.isNotEmpty(config.getPassword())) {
      MongoCredential credential = MongoCredential.createCredential(config.getUsername(),
        config.getDatabase(), config.getPassword().toCharArray());

      mongoClient = new MongoClient(new ServerAddress(config.getHost()), Collections.singletonList(credential));
    }

    this.setMongoClient(mongoClient);
  }

  public MongoClient getMongoClient() {
    return mongoClient;
  }

  public void setMongoClient(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }
}
