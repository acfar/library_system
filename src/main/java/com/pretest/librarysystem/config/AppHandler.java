package com.pretest.librarysystem.config;


import com.pretest.librarysystem.config.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppHandler {
    @ExceptionHandler(value = HttpClientErrorException.class)
    private ResponseEntity<Object> handleHttpClientErrorException(
            HttpClientErrorException httpClientErrorException) {
        return new ResponseEntity<>(
                httpClientErrorException.getResponseBodyAsString(StandardCharsets.UTF_8),
                httpClientErrorException.getStatusCode());
    }


    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<Object> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(
                buildResponse(exception.getStatus(), exception.getMessage()), exception.getHttpStatus());
    }

    private Map<String, String> buildResponse(String status, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return response;
    }
}
