package com.jeyrs.spark;

import com.jeyrs.spark.configuration.AppConfig;
import com.jeyrs.spark.model.Model;
import com.jeyrs.spark.morphia.MongoConfig;
import com.jeyrs.spark.morphia.provider.MorphiaProvider;
import com.jeyrs.spark.redis.RedisConfig;
import com.jeyrs.spark.redis.provider.RedisProvider;
import org.codehaus.jackson.map.ObjectMapper;

class ProviderFacade {
  private static final String REDIS = "redis";
  private static final String MONGO = "mongo";
  private AppConfig config;
  private ObjectMapper objectMapper;

  ProviderFacade(AppConfig appConfig, ObjectMapper objectMapper) {
    this.config = appConfig;
    this.objectMapper = objectMapper;
  }

  private AppConfig.Database getDatabase(AppConfig config, String name) {
    return config.getDatabases()
      .stream()
      .filter(db -> db.getName().equals(name) && db.getEnabled())
      .findFirst()
      .orElseThrow(
        () -> new IllegalArgumentException("Failed to find suitable configured database")
      );
  }


  <T extends Model>RedisProvider<T> redis(Class<T> clazz) {
    RedisConfig redisConfig = new RedisConfig()
      .withConnection(getDatabase(config, REDIS).getHost());
    return new RedisProvider<>(redisConfig, clazz, objectMapper);

  }

  <T extends Model>MorphiaProvider<T> mongo(Class<T> clazz) {
    AppConfig.Database database = getDatabase(config, MONGO);
    MongoConfig mongoConfig = new MongoConfig()
      .withDatabase(database.getName())
      .withUsername(database.getUsername())
      .withPassword(database.getPassword())
      .withHost(database.getHost());

    return new MorphiaProvider<>(mongoConfig, clazz);
  }
}
