package com.example.nukatokenajwt.entity.response;

import com.example.nukatokenajwt.entity.Gender;
import lombok.Data;

@Data
public class UserInfoResponse {

    private String userName;
    private Gender gender;
    private String userPic;


    //TODO DODAJ RESPONSE USER
}
