package com.jeyrs.spark;

import com.jeyrs.spark.configuration.AppConfig;
import com.jeyrs.spark.morphia.MongoConfig;
import com.jeyrs.spark.morphia.model.MorphiaProduct;
import com.jeyrs.spark.morphia.provider.MorphiaProvider;
import com.jeyrs.spark.redis.RedisConfig;
import com.jeyrs.spark.redis.model.RedisProduct;
import com.jeyrs.spark.redis.provider.RedisProvider;
import com.jeyrs.spark.resources.ProductResource;
import com.jeyrs.spark.services.ProductService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import spark.servlet.SparkApplication;

import java.io.IOException;
import java.io.InputStream;

public class Main implements SparkApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
  public void init() {
    LOGGER.info("Starting application");
  }

  public static void main(String[] args) {
    try {
      new Main().initWithRoutes();
    } catch (IOException e) {
      LOGGER.error("Error starting app");
    }
  }

  private void initWithRoutes() throws IOException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("config.yml");
    AppConfig config = new Yaml().loadAs(stream, AppConfig.class);

    ObjectMapper objectMapper = new ObjectMapper();
    ProductService<MorphiaProduct> morphiaProductService = new ProductService<>(mongoProvider(config));
    ProductService<RedisProduct> redisProductService = new ProductService<>(redisProvider(config));

    // Step 1: init resources
    new ProductResource("/morphia", morphiaProductService, objectMapper);
    new ProductResource("/redis", redisProductService, objectMapper);
  }

  private RedisProvider<RedisProduct> redisProvider(AppConfig config) {
    RedisConfig redisConfig = new RedisConfig()
      .withConnection(getDatabase(config, "redis").getHost());
    return new RedisProvider<>(redisConfig, RedisProduct.class, new ObjectMapper());
  }

  private MorphiaProvider<MorphiaProduct> mongoProvider(AppConfig config) {
    AppConfig.Database database = getDatabase(config, "mongo");
    MongoConfig mongoConfig = new MongoConfig()
      .withDatabase(database.getName())
      .withUsername(database.getUsername())
      .withPassword(database.getPassword())
      .withHost(database.getHost());

    return new MorphiaProvider<>(mongoConfig, MorphiaProduct.class);
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
}
