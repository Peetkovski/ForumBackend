package com.example.nukatokenajwt.service;

import com.example.nukatokenajwt.dao.RoleDao;
import com.example.nukatokenajwt.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {


    private final RoleDao roleDao;

    public Role createRole(Role role){


        return roleDao.save(role);
    }
}
