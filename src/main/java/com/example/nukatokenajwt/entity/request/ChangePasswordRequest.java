package com.example.nukatokenajwt.entity.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String token;
    private String userPassword;
}
