package com.example.nukatokenajwt.controller;

import com.example.nukatokenajwt.entity.Role;
import com.example.nukatokenajwt.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/createRole")
    public Role createNewRole(@RequestBody Role role){

       return roleService.createRole(role);
    }


}
