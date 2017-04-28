package com.jeyrs.spark;

import com.jeyrs.spark.configuration.AppConfig;
import com.jeyrs.spark.morphia.MongoConfig;
import com.jeyrs.spark.morphia.provider.ProductProvider;
import com.jeyrs.spark.resources.ProductResource;
import com.jeyrs.spark.services.ProductService;
import org.codehaus.jackson.map.ObjectMapper;
import org.yaml.snakeyaml.Yaml;
import spark.servlet.SparkApplication;

import java.io.IOException;
import java.io.InputStream;

public class Main implements SparkApplication {
  public void init() {
    try {
      new Main().initWithRoutes();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      new Main().initWithRoutes();
    } catch (IOException e) {
      e.printStackTrace();
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

    final ProductService productService = new ProductService(new ProductProvider(databaseConfig));

    // Step 1: init resources
    new ProductResource(productService, new ObjectMapper());
  }
}
