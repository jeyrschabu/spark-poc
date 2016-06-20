package com.jeyrs.spark.services;

import com.google.gson.Gson;
import com.jeyrs.spark.data.ModelProvider;
import com.jeyrs.spark.data.MongoConnectionConfig;
import com.jeyrs.spark.models.Product;

import java.util.List;

public class ProductService extends BaseService<Product> {
    private static final String INITIAL_DATA = "products.json";

    public ProductService setDataProvider(MongoConnectionConfig config) {
        super.setDataProvider(config, Product.class);
        return this;
    }

    protected String getInitialJsonData() {
        return INITIAL_DATA;
    }

    protected ModelProvider getProvider() {
        return modelProvider;
    }

    public List<Product> findByCategory(String category) {
        return findMany("category", category);
    }

    public Product create(String productJson) {
        Product product = new Gson().fromJson(productJson, Product.class);
        return (Product) getProvider().update(product);
    }

    public Product update(String productJson) {
        Product product = new Gson().fromJson(productJson, Product.class);
        return (Product) getProvider().update(product);
    }
}
