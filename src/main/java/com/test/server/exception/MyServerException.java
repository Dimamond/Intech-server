package com.test.server.exception;

import org.springframework.http.HttpStatus;

public class MyServerException extends RuntimeException {

    private HttpStatus httpStatus;

    public MyServerException(String message) {
        super(message);
    }


    public MyServerException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public MyServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
