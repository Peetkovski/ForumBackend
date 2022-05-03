package com.example.nukatokenajwt.controller;

import com.example.nukatokenajwt.entity.User;
import com.example.nukatokenajwt.entity.request.ChangePasswordRequest;
import com.example.nukatokenajwt.entity.request.EmailRequest;
import com.example.nukatokenajwt.entity.response.UserInfoResponse;
import com.example.nukatokenajwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * The type User controller.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * Get restore password code email request.
     *
     * @param emailRequest the email request
     * @return the email request
     */
    @PostMapping("/getCode")
    public EmailRequest getRestorePasswordCode(@RequestBody EmailRequest emailRequest){
        userService.sendRestorePasswordCode(emailRequest);
        return emailRequest;
    }

    /**
     * Change user password user.
     *
     * @param changePasswordRequest the change password request
     * @return the user
     */
    @PostMapping("/changePassword")
    public User changeUserPassword(@RequestBody ChangePasswordRequest changePasswordRequest){

        return userService.changeUserPassword(changePasswordRequest);
    }


    /**
     * Register user user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    /**
     * Find user user info response.
     *
     * @param username the username
     * @return the user info response
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('User')")
    public UserInfoResponse findUser(@PathVariable("username") String username){

      UserInfoResponse userInfoResponse = userService.findUser(username);

        return userInfoResponse;
    }


    /**
     * Init roles and user.
     */
    @PostConstruct
    public void initRolesAndUser(){
        userService.initRoleAndUser();
    }


    /**
     * For admin string.
     *
     * @return the string
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this is only for admin";
    }

    /**
     * Hello string.
     *
     * @return the string
     */
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    /**
     * For user string.
     *
     * @return the string
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "this is only for user";
    }

}
