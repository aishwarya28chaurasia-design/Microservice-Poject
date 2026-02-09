package com.learncode.employee.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),exception.getStatus());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),exception.getStatus());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameterException(MissingParameterException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),exception.getStatus());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(),exception.getStatus());
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }
}
