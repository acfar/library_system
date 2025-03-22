package com.pretest.librarysystem.config.exception;

import org.springframework.http.HttpStatus;
public class BusinessException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String status;
    private final String message;

    public BusinessException(HttpStatus httpStatus, String status, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}