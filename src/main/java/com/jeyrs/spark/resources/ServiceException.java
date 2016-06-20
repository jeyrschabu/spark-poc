package com.jeyrs.spark.resources;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by jkiluf on 4/14/16.
 */

public class ServiceException extends Exception {

    private int statusCode = HttpServletResponse.SC_BAD_REQUEST;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public ServiceException setStatusCode(int code) {
        this.statusCode = code;
        return this;
    }
}
