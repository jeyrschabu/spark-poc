package com.jeyrs.spark.resources;

import com.jeyrs.spark.model.Product;
import com.jeyrs.spark.services.ProductService;
import org.codehaus.jackson.map.ObjectMapper;

import static spark.Spark.*;

public class ProductResource extends GeneralResource {
  private final ProductService productService;
  private final ObjectMapper objectMapper;

  public ProductResource(ProductService productService, ObjectMapper objectMapper) {
    this.productService = productService;
    this.objectMapper = objectMapper;
    routes();
  }

  @Override
  ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  protected void routes() {
    // Read All
    get(CONTEXT + "/products", (request, response) -> this.productService.findAll(), json());

    // Read One
    get(CONTEXT + "/products/:id", (request, response) -> this.productService.find(request.params(":id")), json());

    // Create
    post(CONTEXT + "/products", (request, response) -> {
      Product product = objectMapper.readValue(request.body(), Product.class);
      return this.productService.create(product);
    }, json());

    // Update
    put(CONTEXT + "/products/:id", (request, response) -> {
      Product product = objectMapper.readValue(request.body(), Product.class);
      return this.productService.update(product);
    } , json());

    // Delete
    delete(CONTEXT + "/products/:id", (request, response) -> this.productService.remove(request.params(":id")), json());
  }
}
