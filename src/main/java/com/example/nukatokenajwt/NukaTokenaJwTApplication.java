package com.example.nukatokenajwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NukaTokenaJwTApplication {

    public static void main(String[] args) {
        SpringApplication.run(NukaTokenaJwTApplication.class, args);
    }

}
