package com.example.nukatokenajwt.service;

public class PasswordTokenIsExpiredException extends RuntimeException {
    public PasswordTokenIsExpiredException(String msg){
        super(msg);
    }
}
