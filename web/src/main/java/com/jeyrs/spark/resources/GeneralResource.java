package com.jeyrs.spark.resources;

import com.jeyrs.spark.constants.ApplicationConstants;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.ResponseTransformer;

import static spark.Spark.before;
import static spark.Spark.exception;

abstract class GeneralResource implements ApplicationConstants {
  static final Logger LOGGER = LoggerFactory.getLogger(GeneralResource.class);

  abstract ObjectMapper getObjectMapper();

  GeneralResource() {
    before("/*", (request, response) -> filterRequest(request)); //before filter

    //exception handling
    exception(ServiceException.class, (e, request, response) -> {
      response.status(404);
      response.body("Resource not found");
    });
  }

  private void filterRequest(Request request) {
    LOGGER.info("{} Request origin IP Address {} with userAgent {} ", request.requestMethod(), request.raw().getRemoteAddr(), request.userAgent());
  }

  protected abstract void routes();

  ResponseTransformer json() {
    return object -> getObjectMapper().writeValueAsString(object);
  }
}
