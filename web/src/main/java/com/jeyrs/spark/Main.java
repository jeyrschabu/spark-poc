package com.jeyrs.spark;

import com.jeyrs.spark.configuration.AppConfig;
import com.jeyrs.spark.morphia.MongoConfig;
import com.jeyrs.spark.morphia.model.MorphiaProduct;
import com.jeyrs.spark.morphia.provider.MorphiaProvider;
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
  static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
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

    StorageConfig databaseConfig = new MongoConfig()
      .withDatabase(database.getName())
      .withUsername(database.getUsername())
      .withPassword(database.getPassword())
      .withHost(database.getHost());

    final ProductService<MorphiaProduct> productService = new ProductService<>(
      new MorphiaProvider<>(databaseConfig, MorphiaProduct.class)
    );

    // Step 1: init resources
    new ProductResource(productService, new ObjectMapper());
  }
}
