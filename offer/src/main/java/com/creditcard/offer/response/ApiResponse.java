package com.creditcard.offer.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
public class ApiResponse<T> {
    private String message;
    private HttpStatus status;
    private T data;

    public ApiResponse(String message, HttpStatus status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
