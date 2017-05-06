package com.jeyrs.spark;

import com.jeyrs.spark.configuration.AppConfig;
import com.jeyrs.spark.morphia.model.MorphiaProduct;
import com.jeyrs.spark.redis.model.RedisProduct;
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
    ProviderFacade providerFacade = new ProviderFacade(config, objectMapper);

    ProductService<MorphiaProduct> morphiaProductService = new ProductService<>(providerFacade.mongo(MorphiaProduct.class));
    ProductService<RedisProduct> redisProductService = new ProductService<>(providerFacade.redis(RedisProduct.class));

    // Step 1: init resources
    new ProductResource("/morphia", morphiaProductService, objectMapper);
    new ProductResource("/redis", redisProductService, objectMapper);
  }
}
