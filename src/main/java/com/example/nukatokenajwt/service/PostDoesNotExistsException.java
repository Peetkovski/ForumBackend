package com.example.nukatokenajwt.service;

public class PostDoesNotExistsException extends RuntimeException {
    public PostDoesNotExistsException(String m) {
        super(m);
    }
}
