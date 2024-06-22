package com.harsh.inspireBlog.Exception;

public class BlogNotFoundException extends RuntimeException {

    public BlogNotFoundException(String message){
        super(message);
    }
}
