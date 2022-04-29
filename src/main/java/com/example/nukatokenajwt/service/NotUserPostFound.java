package com.example.nukatokenajwt.service;

public class NotUserPostFound extends RuntimeException {
    public NotUserPostFound(String msg){
        super(msg);
    }
}
