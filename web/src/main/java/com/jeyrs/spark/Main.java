package com.jeyrs.spark;

import com.jeyrs.spark.configuration.ApplicationConfig;
import com.jeyrs.spark.morphia.provider.ProductProvider;
import com.jeyrs.spark.resources.ProductResource;
import com.jeyrs.spark.services.ProductService;
import org.aeonbits.owner.ConfigFactory;
import org.codehaus.jackson.map.ObjectMapper;
import spark.servlet.SparkApplication;

import static com.jeyrs.spark.constants.ApplicationConstants.*;

public class Main implements SparkApplication {
  private ApplicationConfig serverConfig = ConfigFactory.create(ApplicationConfig.class);

  public void init() {
    new Main().initWithRoutes();
  }

  public static void main(String[] args) {
    new Main().initWithRoutes();
  }

  private void initWithRoutes() {
    DatabaseConfig databaseConfig = new DatabaseConfig()
      .withDatabase(serverConfig.db() == null ? DEFAULT_DB : serverConfig.db())
      .withUsername(serverConfig.dbUser())
      .withPassword(serverConfig.dbPass())
      .withHost(serverConfig.dbHost() == null ? DEFAULT_HOST : serverConfig.dbHost());

    final ProductService productService = new ProductService(new ProductProvider(databaseConfig));

    // Step 1: init resources
    new ProductResource(productService, new ObjectMapper());
  }
}
