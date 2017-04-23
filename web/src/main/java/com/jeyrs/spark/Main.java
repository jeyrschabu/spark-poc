package com.jeyrs.spark;

import com.jeyrs.spark.configuration.ApplicationConfig;
import com.jeyrs.spark.data.MongoConnectionConfig;
import com.jeyrs.spark.resources.HomeResource;
import com.jeyrs.spark.resources.ProductResource;
import com.jeyrs.spark.services.ProductService;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import spark.servlet.SparkApplication;

import java.util.Arrays;

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
        // Step 1: initialize database
        String database  = serverConfig.db() == null ? DEFAULT_DB : serverConfig.db();
        String mongoHost = serverConfig.dbHost() == null ? DEFAULT_HOST : serverConfig.dbHost();
        String userName  = serverConfig.dbUser();
        String password  = serverConfig.dbPass();

        MongoClient mongoClient = new MongoClient();

        if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
            MongoCredential credential = MongoCredential.createCredential(userName,
                    database, password.toCharArray());
            mongoClient = new MongoClient(new ServerAddress(mongoHost), Arrays.asList(credential));
        }

        final MongoConnectionConfig dbCfg = new MongoConnectionConfig(mongoClient, database);

        // Step 1: init services
        final ProductService productService = new ProductService().setDataProvider(dbCfg);

        // Step 1: init resources
        new ProductResource(productService);
        new HomeResource();
    }
}
