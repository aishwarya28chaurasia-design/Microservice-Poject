package com.learncode.address_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learncode.address_service.exception.BadRequestException;
import com.learncode.address_service.exception.CustomException;
import com.learncode.address_service.exception.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        System.out.println("CustomErrorDecoder.decode() called for method: " + methodKey + ", status: " + response.status());

        int status = response.status();
        if (status == 503){
            System.out.println("Returning BadRequestException for service down");
            return new BadRequestException("Employee Service is down, Please try again later ", HttpStatus.SERVICE_UNAVAILABLE);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        try(InputStream is = response.body().asInputStream()) {
            ErrorResponse errorResponse = objectMapper.readValue(is, ErrorResponse.class);
            System.out.println("Returning CustomException with message: " + errorResponse.getMessage());
            return new CustomException(errorResponse.getMessage(), errorResponse.getStatus());
        } catch (IOException e) {
            System.out.println("IOException in CustomErrorDecoder, returning INTERNAL_SERVER_ERROR");
            throw new CustomException("INTERNAL_SERVER_ERROR");
        }
    }
}
