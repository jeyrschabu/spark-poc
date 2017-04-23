package com.jeyrs.spark;

import com.jeyrs.spark.configuration.ApplicationConfig;
import com.jeyrs.spark.provider.DataProvider;
import com.jeyrs.spark.resources.HomeResource;
import com.jeyrs.spark.resources.ProductResource;
import com.jeyrs.spark.services.ProductService;
import org.aeonbits.owner.ConfigFactory;
import spark.servlet.SparkApplication;
import java.util.List;

import static com.jeyrs.spark.constants.ApplicationConstants.*;

public class Main implements SparkApplication {
  protected ApplicationConfig serverConfig = ConfigFactory.create(ApplicationConfig.class);

  public void init() {
    new Main().initWithRoutes();
  }

  public static void main(String[] args) {
    new Main().initWithRoutes();
  }

  private void initWithRoutes() {
    DatabaseConfig dbconfig = new DatabaseConfig()
      .withDatabase(serverConfig.db() == null ? DEFAULT_DB : serverConfig.db())
      .withUsername(serverConfig.dbUser())
      .withPassword(serverConfig.dbPass())
      .withHost(serverConfig.dbHost() == null ? DEFAULT_HOST : serverConfig.dbHost());

    DataStore datastore = getDatastores(dbconfig)
      .stream()
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("Could not find a suitable configured data store"));

    List<DataProvider> providers = getProviders(datastore);
    final ProductService productService = new ProductService(providers);


    // Step 1: init resources
    new ProductResource(productService);
    new HomeResource();
  }

  private List<DataProvider> getProviders(DataStore datastore) {
    return null;//TODO: implement
  }


  public List<DataStore> getDatastores(DatabaseConfig dbconfig) {
    return null;//TODO: implement
  }

}
