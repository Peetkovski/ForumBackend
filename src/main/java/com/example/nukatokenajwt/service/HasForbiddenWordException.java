package com.example.nukatokenajwt.service;

public class HasForbiddenWordException extends RuntimeException {
    public HasForbiddenWordException(String s) {
        super(s);
    }
}
