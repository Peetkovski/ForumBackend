package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.RoleDao;
import com.example.nukatokenajwt.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createRole(Role role){


        return roleDao.save(role);
    }
}
