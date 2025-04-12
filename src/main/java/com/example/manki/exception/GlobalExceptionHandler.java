package com.example.manki.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleTaskNotFound(TaskNotFoundException e){
        return new ResponseEntity<>(new ErrorDetails("Task not found",e.getMessage()), HttpStatus.NOT_FOUND);   
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGlobalsException(Exception e){
        return new ResponseEntity<>(new ErrorDetails("Internal Server Error",e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
