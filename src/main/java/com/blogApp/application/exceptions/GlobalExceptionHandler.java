package com.blogApp.application.exceptions;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogApp.application.payloads.ApiResponce;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponce> resourceNotFoundExceptionHandler(
            ResourceNotFoundException exception) {

        ApiResponce apiResponce =
                new ApiResponce(exception.getMessage(), false);

        return new ResponseEntity<>(apiResponce, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> argumentsValidationExceptionHandler(
            MethodArgumentNotValidException exception) {

        HashMap<String, String> validationErrors = new HashMap<>();

        exception.getBindingResult()
                 .getAllErrors()
                 .forEach(error -> {
                     String fieldName = ((FieldError) error).getField();
                     String message = error.getDefaultMessage();
                     validationErrors.put(fieldName, message);
                 });

        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
}
