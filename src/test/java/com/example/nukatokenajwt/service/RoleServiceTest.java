package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.RoleDao;
import com.example.nukatokenajwt.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)

class RoleServiceTest {

    RoleService roleService;

    @Mock
    RoleDao roleDao;

    @BeforeEach
    void setUp() {
        roleService = new RoleService(roleDao);
    }

    @Test
    void createRole() {
        Role role = new Role(
                "User",
                "Default"
        );
        roleService.createRole(role);

    }
}
