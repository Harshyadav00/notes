package com.harsh.inspireBlog.Controller;

import com.harsh.inspireBlog.Exception.BlogNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<ErrorResponseException> handleBlogNotFound(BlogNotFoundException ex) {
        ErrorResponseException errorResponse = new ErrorResponseException(HttpStatus.NOT_FOUND, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
