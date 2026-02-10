package com.learncode.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),exception.getStatus());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }
}
