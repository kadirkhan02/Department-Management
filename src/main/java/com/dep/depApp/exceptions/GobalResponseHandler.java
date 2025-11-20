package com.dep.depApp.exceptions;


import com.dep.depApp.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GobalResponseHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex)
    {
        Apierror apierror=Apierror.builder().status(HttpStatus.NOT_FOUND).message(ex.getMessage())

        .build();

        return buildErrorResponseEntity(apierror);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>>handleIllegalArgument(IllegalArgumentException ex)
    {
        Apierror apierror=Apierror.builder()
                .status(HttpStatus.NOT_FOUND).message(ex.getMessage())
                .build();

        return buildErrorResponseEntity(apierror);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>>handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
    {
        List<String>l=ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error ->error.getDefaultMessage())
                .collect(Collectors.toList());

        Apierror apierror=Apierror.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subErrors(l)
                .build();

        return buildErrorResponseEntity(apierror);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
        Apierror apiError = Apierror.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }


    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(Apierror apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex)
//    {
//        return new ResponseEntity<>(errorBody(ex.getMessage(),404), HttpStatus.NOT_FOUND);
//    }
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
//        return new ResponseEntity<>(errorBody(ex.getMessage(), 400), HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
//
//        Map<String, Object> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors()
//                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//
//    // 4️⃣ Fallback (Internal Server Error)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGeneralErrors(Exception ex) {
//        return new ResponseEntity<>(errorBody("Something went wrong", 500), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private Map<String, Object> errorBody(String message, int status) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", message);
//        body.put("status", status);
//        body.put("timestamp", LocalDateTime.now());
//        return body;
//    }
}
