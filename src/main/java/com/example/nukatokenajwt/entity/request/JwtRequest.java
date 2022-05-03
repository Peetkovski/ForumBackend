package com.example.nukatokenajwt.entity.request;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
