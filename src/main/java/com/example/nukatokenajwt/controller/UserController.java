package com.example.nukatokenajwt.controller;

import com.example.nukatokenajwt.entity.Role;
import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.entity.UserInfoResponse;
import com.example.nukatokenajwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('User')")
    public UserInfoResponse findUser(@PathVariable("username") String username){

      UserInfoResponse userInfoResponse = userService.findUser(username);

        return userInfoResponse;
    }


    @PostConstruct
    public void initRolesAndUser(){
        userService.initRoleAndUser();
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this is only for admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "this is only for user";
    }

}
