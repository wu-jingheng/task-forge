package com.moxie.task_forge.controller;

import com.moxie.task_forge.common.ApiResponse;
import com.moxie.task_forge.exception.TaskNotFoundException;
import com.moxie.task_forge.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            TaskNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleTaskNotFound(RuntimeException ex) {
        ApiResponse<Void> response = new ApiResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericExceptions(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
