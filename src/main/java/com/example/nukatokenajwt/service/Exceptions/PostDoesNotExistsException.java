package com.example.nukatokenajwt.service.Exceptions;

public class PostDoesNotExistsException extends RuntimeException {
    public PostDoesNotExistsException(String m) {
        super(m);
    }
}
