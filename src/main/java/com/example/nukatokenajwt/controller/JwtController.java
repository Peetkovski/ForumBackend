package com.example.nukatokenajwt.controller;

import com.example.nukatokenajwt.entity.request.JwtRequest;
import com.example.nukatokenajwt.entity.response.JwtResponse;
import com.example.nukatokenajwt.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:4200")
@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;


    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws  Exception{

        return jwtService.createJwtToken(jwtRequest);

    }


}
