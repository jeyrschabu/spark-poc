package com.jeyrs.spark.resources;

import com.jeyrs.spark.services.ProductService;

import static spark.Spark.*;

public class ProductResource extends GeneralResource {
    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
        routes();
    }

    protected void routes() {
        // Read All
        get(CONTEXT+"/products", (request, response) -> this.productService.findAll(), json());

        // Read One
        get(CONTEXT+"/products/:id", (request, response) -> this.productService.find(request.params(":id")), json());

        // Create
        post(CONTEXT+"/products", (request, response) -> this.productService.create(request.body()), json());

        // Update
        put(CONTEXT + "/products/:id", (request, response) -> this.productService.update(request.body()), json());

        // Delete
        delete(CONTEXT + "/products/:id", (request, response) -> this.productService.remove(request.params(":id")), json());
    }
}
