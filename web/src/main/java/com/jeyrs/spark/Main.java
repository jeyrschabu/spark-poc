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
import java.util.List;

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

    AppConfig.Database database = config.getDatabases()
      .stream()
      .filter(AppConfig.Database::getEnabled)
      .findFirst()
      .orElseThrow(
        () -> new IllegalArgumentException("Failed to find suitable configured database")
      );

    MongoConfig mongoConfig = new MongoConfig()
      .withDatabase(database.getName())
      .withUsername(database.getUsername())
      .withPassword(database.getPassword())
      .withHost(database.getHost());

    RedisConfig redisConfig = new RedisConfig()
      .withConnection(database.getHost());

    final ProductService<MorphiaProduct> productService1 = new ProductService<>(
      new MorphiaProvider<>(mongoConfig, MorphiaProduct.class)
    );

    final ProductService<RedisProduct> productService2 = new ProductService<>(
      new RedisProvider<>(redisConfig, RedisProduct.class, new ObjectMapper())
    );

    productService2.create(
      new RedisProduct()
        .withCategory("Test category")
        .withPrice(10.0)
        .withName("test product")
    );

    productService2.create(
      new RedisProduct()
        .withCategory("Test category 2")
        .withPrice(12.0)
        .withName("test product 2")
    );

    List<RedisProduct> productList = productService2.findAll();

    // Step 1: init resources
    new ProductResource(productService1, new ObjectMapper());
  }
}
