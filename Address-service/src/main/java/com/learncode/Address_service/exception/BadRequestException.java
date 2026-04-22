package com.learncode.Address_service.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public BadRequestException(String message) {
        //super(message);
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public BadRequestException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
