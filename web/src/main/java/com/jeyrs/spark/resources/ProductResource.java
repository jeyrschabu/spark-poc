package com.jeyrs.spark.resources;

import com.jeyrs.spark.model.Product;
import com.jeyrs.spark.services.ProductService;
import org.codehaus.jackson.map.ObjectMapper;

import static spark.Spark.*;

public class ProductResource extends BaseResource {
  private final ProductService productService;
  private final ObjectMapper objectMapper;
  private String prefix;

  public ProductResource(String prefix,
                         ProductService productService,
                         ObjectMapper objectMapper) {
    this.productService = productService;
    this.objectMapper = objectMapper;
    this.prefix = prefix;
    routes();
  }

  @Override
  ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  protected void routes() {
    // Read All
    get(CONTEXT + prefix + "/products", (request, response) -> this.productService.findAll(), json());

    // Read One
    get(CONTEXT + prefix + "/products/:id", (request, response) -> this.productService.find(request.params(":id")), json());

    // Create
    post(CONTEXT + prefix + "/products", (request, response) -> {
      Product product = objectMapper.readValue(request.body(), Product.class);
      return this.productService.create(product);
    }, json());

    // Update
    put(CONTEXT + prefix + "/products/:id", (request, response) -> {
      Product product = objectMapper.readValue(request.body(), Product.class);
      return this.productService.update(product);
    } , json());

    // Delete
    delete(CONTEXT + prefix + "/products/:id", (request, response) -> this.productService.remove(request.params(":id")), json());
  }
}
